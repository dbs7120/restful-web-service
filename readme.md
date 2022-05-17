# Spring Boot 를 이용한 RestFul Web Services 개발

<hr />
<hr />

+ 스프링 부트 설정 파일
  + properties
    + 단일라인 구성 (name=value)
    + ```properties
       example.jdbc.url=127.0.0.1 
       example.jdbc.port=3306 
       example.jdbc.user=user 
       example.jdbc.password=password 
      ```
    
  + yml
     + 계층적 구조 형태
     + 들여쓰기로 계층 구조 
     + ```yaml
        example: 
         jdbc: 
          url: 127.0.0.1 
          port: 3306 
          user: user 
          password: password
       ```

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


<hr />

ServletUriComponentsBuilder
+ 특정 값을 포함한 URI를 전달 해야 할 때 사용
+ 사용자가 요청한 URI를 가져온 다음 path를 통해 원하는 정보를 입력
+ buildAndExpand에 원하느 값을 넣어주면 path({변수})에 추가되어 UIR가 구성
+ ResponseEntity를 통해 사용자에게 전달
```java
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = service.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
```
![img.png](readmeImg/img_6.png)

<hr />

AOP를 활용한 예외 처리(@ControllerAdvice)
```JAVA
@ControllerAdvice // 모든 컨트롤러가 실행될때 반드시 Advice 가지는 Bean 을 찾아내서 실행하게 됨 -> 전역에서 발생하는 예외를 잡아 처리 할 수 있음 ,AOP(관점지향프로그래밍) 활용
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

```