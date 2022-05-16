package com.example.restfulwebservice.user;

import org.springframework.web.bind.annotation.*;

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
        return service.findOne(id);
    }

    @PostMapping("/users")
    public void createUser(@RequestBody User user) {
        User savedUser = service.save(user);
    }
}
