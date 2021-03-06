package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value = {"password", "ssn"}) // 클래스 단위 Json 필터 (response 데이터에서 제외됨)
@NoArgsConstructor
//@JsonFilter("UserInfo")
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Size(min = 2, message = "Name은 두 글자 이상 입력해 주세요") // 문자열 길이 Validation
    @ApiModelProperty(notes = "사용자의 이름을 입력해 주세요")
    private String name;

    @Past
    @ApiModelProperty(notes = "사용자의 등록일을 입력해 주세요")
    private Date joinDate;

//  @JsonIgnore // 필드 단위 Json 필터 (response 데이터에서 제외됨)
    @ApiModelProperty(notes = "사용자 패스워드를 입력해 주세요")
    private String password;

    @ApiModelProperty(notes = "사용자 주민등록번호를 입력해 주세요")
    private String ssn;
}
