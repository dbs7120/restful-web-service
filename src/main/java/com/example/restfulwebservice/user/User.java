package com.example.restfulwebservice.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
public class User {
    private Integer id;
    @Size(min = 2, message = "Name은 두 글자 이상 입력해 주세요") // 문자열 길이 Validation
    private String name;
    @Past
    private Date joinDate;
}
