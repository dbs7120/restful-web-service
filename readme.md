# Spring Boot 를 이용한 RestFul Web Services 개발

<hr />
<hr />

+ 스프링 부트 설정 파일
  + properties
    + 단일라인 구성 (name=value)
    + <pre>
       example.jdbc.url=127.0.0.1 
       example.jdbc.port=3306 
       example.jdbc.user=user 
       example.jdbc.password=password 
      </pre>
    
  + yml
     + 계층적 구조 형태
     + 들여쓰기로 계층 구조 
     + <pre>
        example: 
         jdbc: 
          url: 127.0.0.1 
          port: 3306 
          user: user 
          password: password
       </pre>

<hr />

Spring Boot 동작 관련 method
+ DispatcherServletAutoConfiguration
  + DispatcherServlet 관리하는 자동 관리 메소드 
  + DispatcherServlet : 사용자의 요청을 처리하는 게이트웨이 역활
  
+ HttpMessageConvertersAutoConfiguration
  + 사용자(클라이언트) 에게 요청한 결과 Json 형태로 변환하여 반환하는 설정 메소드

<hr />

![img_4.png](readmeImg/img_4.png)

DispatcherServlet
- 클라이언트의 모든 요청을 한곳으로 반아서 처리
- 요청에 맞는 Handler 로 요청을 전달
- Handler 의 실행 결과를 Http Response 형태로 만들어서 반환

![img_5.png](readmeImg/img_5.png)

RestController
- Spring4 부터 @RestController 지원
- View 를 갖지 않는 REST Data(JSON/XML) 를 반환
