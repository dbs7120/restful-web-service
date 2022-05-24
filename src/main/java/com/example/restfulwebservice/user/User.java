package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value = {"password", "ssn"}) // 클래스 단위 Json 필터 (response 데이터에서 제외됨)
@NoArgsConstructor
@JsonFilter("UserInfo")
public class User {

    private Integer id;

    @Size(min = 2, message = "Name은 두 글자 이상 입력해 주세요") // 문자열 길이 Validation
    private String name;

    @Past
    private Date joinDate;

//  @JsonIgnore // 필드 단위 Json 필터 (response 데이터에서 제외됨)
    private String password;

    private String ssn;
}
