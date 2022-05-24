package com.example.restfulwebservice.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

// Service: 비즈니스 로직
@Service // Bean 스테레오타입
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 3;

    // static 초기화 블럭
    // static 부르는 상황이오면 자동으로 초기화(클래스 메모리 로드), 단 한번만 호출됨
    static {
        users.add(new User(1,"Kenneth", new Date(), "pass1", "701010-1111111"));
        users.add(new User(2,"Alice", new Date(), "pass2", "801010-2222222"));
        users.add(new User(3,"Elena", new Date(), "pass3", "901010-1111111"));
    }

    // 전체 사용자 조회
    public List<User> findAll() {
        return users;
    }

    // 사용자 등록
    public User save(User user){
        if (user.getId() == null) {
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    // 사용자 조회 (by ID)
    public User findOne(int id){
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator(); // 열거형(Iterator) Data, 순차탐색용

        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) { // if found data
                iterator.remove();
                return user;
            }
        }

        return null; // if not found data
    }

    public User updateUser(User user, int id) {
        User check = this.findOne(id);
        if (check == null)
            return null;
        check.setName(user.getName());

        return check;
    }
}
