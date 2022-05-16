package com.example.restfulwebservice.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// lombok 사용 <- 인텔리제이 Structure 항목에 나타남

@Data // constructor(NoArgsConstructor), setter, getter, toString 자동 생성(AllArgsConstructor 제외)
//@NoArgsConstructor // 빈 생성자(기본생성자)
@AllArgsConstructor // 모든 필드에 대한 생성자를 자동 생성, 클래스 몸체에 생성자가 정의 된다면 빌드 오류 발생
public class HelloWorldBean {
    private String message;
}
