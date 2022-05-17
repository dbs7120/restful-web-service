package com.example.restfulwebservice.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) { // IoC: 생성자 의존성 주입
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    // GET /users/1 or /users/10 ...  -> HTTP 서버 컨트롤러형태로 넘길때는 문자형태(String)로 넘어감
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) { // 자동 컨버팅 일어남 String -> int
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
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
