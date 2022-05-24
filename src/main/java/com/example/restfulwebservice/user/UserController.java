package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo; // static import
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) { // IoC: 생성자 의존성 주입
        this.service = service;
    }

    @GetMapping("/users")
    public ResponseEntity<MappingJacksonValue> retrieveAllUsers() {

        List<EntityModel<User>> result = new ArrayList<>();
        List<User> users = service.findAll();

        // HATEOAS
        for (User user : users) {
            EntityModel entityModel = EntityModel.of(user);
            entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllUsers()).withSelfRel());
            result.add(entityModel);
        }

        // Filter
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter); // UserDto 필터명 @JsonFilter("UserInfo")
        MappingJacksonValue mapping = new MappingJacksonValue(result);
        mapping.setFilters(filters);

        return ResponseEntity.ok(mapping);
    }

    // GET /users/1 or /users/10 ...  -> HTTP 서버 컨트롤러형태로 넘길때는 문자형태(String)로 넘어감
    @GetMapping("/users/{id}")
    public ResponseEntity<MappingJacksonValue> retrieveUser(@PathVariable int id) { // 자동 컨버팅 일어남 String -> int
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // HATEOAS
        EntityModel entityModel = EntityModel.of(user);

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers()); // linkTo, MethodOn = static import method
        entityModel.add(linkTo.withRel("all-users")); // hyper-media link


        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter); // UserDto 필터명 @JsonFilter("UserInfo")
        MappingJacksonValue mapping = new MappingJacksonValue(entityModel);
        mapping.setFilters(filters);

        return ResponseEntity.ok(mapping);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        // ServletUriComponentsBuilder 요청한 사용자에게 적절한 URI 를 만들고 특정값을 포함한 URI 를 전달 할 수 있음.
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") //buildAndExpand 를 통해 얻은 값이 들어옴
                .buildAndExpand(savedUser.getId())
                .toUri(); // URI 생성

        // return HTTP Status Code 201 Created
        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

    @PutMapping("/users/{id}")
    public void updateUser(@RequestBody User user, @PathVariable int id){
        User updateData = service.updateUser(user, id);
        if(updateData == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", user.getId()));
        }
    }

}
