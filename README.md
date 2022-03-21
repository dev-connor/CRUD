# REST API 배포 프로젝트

Spring boot 와 REST API 를 활용하여 CRUD 를 설계 및 Docker & Travis 로 AWS 에 "자동" 배포하는 프로젝트입니다.

주제: REST API & 자동배포

개발인원: 개인

개발기간: 
- 2022년 3월 14일 ~ 2022년 3월 21일 (일주일)

- 스프링부트: 2.2.7.RELEASE
- 빌드도구: Maven
- 패키징: JAR
- ORM: JPA
- DBMS: MySQL (AWS 의 RDS)
- DB툴: MySQL Workbench
- IDE: IntelliJ
- 버전관리: Git & GitHub

---
목차

1. GitHub 에 push 했을 때 Travis CI 가 감지하여 Docker hub 에 저장하고 AWS 배포 자동화
    - GitHub 에 push
    - Travis CI 테스트
    - Docker hub 저장확인
    - AWS 배포 확인
2. Spring boot 로 개발한 REST API 를 HAL browser 를 통해 CRUD 테스트
    - Read
    - Create
    - Delete
---

**Request Mapping**
- 글목록 /posts
- 글작성 /posts/write
- 글상세 /posts/{id}
- 글수정 /posts/{id}/edit
- 글삭제 /posts/{id}


<!-- 깃허브 푸시 -->
![image](https://user-images.githubusercontent.com/70655507/159131185-fa4d0127-46f0-4d8a-8957-749c30e07233.png)

깃허브에 푸시해 Travis CI 가 Docker 에 담아 AWS 에 자동배포를 하는지 확인해보겠습니다. 

<!-- Travis 테스트 중 -->
![image](https://user-images.githubusercontent.com/70655507/159129871-3b907e24-b252-4164-952d-303745d39fa7.png)

'Running for 5 sec' 라는 글자를 통해 Travis 가 프로젝트를 테스트하고 있습니다.

아래는 테스트 로그를 보여주고 있습니다.

깃허브를 푸시하면 Travis 가 .travis.yml 이라는 파일을 읽습니다.

```yml
language: java

sudo: required

services:
  - docker

before_install:
  - chmod +x mvnw
  - ./mvnw install -DskipTests=true -Dmaven.javadoc.skip=true -B -V

script:
   - ./mvnw test -B

after_success:
  - docker build -t devconnor/guest_book .
  - echo "$DOCKER_PWD" | docker login -u "$DOCKER_ID" --password-stdin
  - docker push devconnor/guest_book

deploy:
  provider: elasticbeanstalk
  region: "ap-northeast-2"
  app: "Guest_book"
  env: "Guestbook-env"
  bucket_name: elasticbeanstalk-ap-northeast-2-615258856034
  bucket_path: "Guest_book"
  on:
    branch: main
  access_key_id: $AWS_KEY
  secret_access_key: $AWS_SECRET_KEY
```

Maven 을 설치하고 테스트하고

after_success: 도커허브에 로그인해 푸시합니다.

deploy: AWS 에 배포합니다.

<!-- 테스트 완료 -->
![image](https://user-images.githubusercontent.com/70655507/159130138-76a2670d-fe02-4964-8f50-f65edef6cc6c.png)

테스트에 성공하면 초록색, 실패하면 빨강색 화면을 띄웁니다.

왼쪽에 'Running' 창이 비워진 것이 보이고 Run for 2 min 이라는 글자가 보입니다.

<!-- 도커허브 확인 -->
![image](https://user-images.githubusercontent.com/70655507/159130343-a96c4add-81c1-4554-85de-3b6c233c9922.png)

'Last pushed a minute ago' 라는 글자와 함께 도커허브에 잘 푸시된 것이 보입니다.

```
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

빌드된 JAR 파일을 open JDK 로 실행하는 코드입니다.

### 개발환경에 사용되는 코드들

**docker-compose-dev**

```yml
version: '3'
services:
  spring-boot:
    build:
      context: .
      dockerfile: Dockerfile.dev
    ports:
      - "80:80"
    volumes:
      - .:/app
```

**Dockerfile.dev**

```
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

<!-- AWS 배포 확인 -->
![image](https://user-images.githubusercontent.com/70655507/159130510-08c4474d-ae42-43a5-9cd9-e0965e004766.png)

상태가 확인이 된 것이 보이며 
- 실행버전: Travis
- 플랫폼: docker 

가 보입니다.

아래는 'Environment update completed successfully.' 로 환경이 성공적으로 업데이트된 것이 보입니다.

<!-- MySQL 워크벤치 -->
![image](https://user-images.githubusercontent.com/70655507/159130558-a6ed727c-4b66-4cf1-a202-1be670ee2b68.png)

AWS RDS 에 Work bench 로 접속한 화면입니다.

<!-- 리스트 뿌리기 -->
![image](https://user-images.githubusercontent.com/70655507/159130661-b3ddd4e1-fcca-449a-8256-44da5b9d0750.png)

화면에 JSON 형태로 잘 뿌려지는 것이 보입니다.

```java
    // 글상세
    @GetMapping("/{id}")
    public EntityModel<Post> getPost(@PathVariable int id) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()) {

            // HATEOAS
            EntityModel<Post> model = new EntityModel<>(post.get());
            WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getPosts());
            model.add(linkTo.withRel("all-posts"));
            return model;
        }
        return null;
    }
```

<!-- POST 버튼 -->
![image](https://user-images.githubusercontent.com/70655507/159130916-48eed968-53e9-46df-b5d1-3f4ef6e50203.png)

<!-- 글 작성 -->
![image](https://user-images.githubusercontent.com/70655507/159130952-3e9a687b-9476-4600-9d22-3c972d1df7ae.png)

**Post.java**

```java
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String content;
    private String writer;

    @CreatedDate
    private LocalDateTime postDate;
}
```

**CrudApplication.java**

```java
@EnableJpaAuditing
@SpringBootApplication
public class CrudApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }
}
```

<!-- 201 Created 상태반환 -->
![image](https://user-images.githubusercontent.com/70655507/159130973-4abe8614-1392-42be-86a9-18f7258b7891.png)

<!-- 글 작성 완료 -->
![image](https://user-images.githubusercontent.com/70655507/159130989-448ffbcf-54dd-4c3b-b971-551660f2124c.png)

글작성이 잘 된것을 볼 수 있습니다.

<!-- 헤테오스 -->
![image](https://user-images.githubusercontent.com/70655507/159131029-9440074f-dd71-4259-b48e-1d23dd1b159a.png)

<!-- 글 삭제 -->
![image](https://user-images.githubusercontent.com/70655507/159131080-f5ec1461-2757-4378-90bd-bee735a6182d.png)

이제 Postman 으로 107번째 글을 삭제해보겠습니다.

<!-- MySQL 워크벤치 확인 -->
![image](https://user-images.githubusercontent.com/70655507/159131122-dd3d2c15-a8e8-4e19-b5f0-a8b984e15cad.png)

107번의 글이 삭제되고 새글이 추가된 것을 볼 수 있습니다. 

@GeneratedValue 에 의해 1번부터 생성되는데 두 번 테스트 후 3번이 작성되었습니다)
