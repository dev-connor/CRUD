# REST API 배포 프로젝트

Spring boot 와 REST API 를 활용하여 CRUD 를 설계 및 Docker & Travis 로 AWS 에 "자동" 배포하는 프로젝트입니다.

주제: REST API & 자동배포

개발인원: 개인

개발기간: 2022년 3월 14일 ~ 2022년 3월

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


![image](https://user-images.githubusercontent.com/70655507/159131185-fa4d0127-46f0-4d8a-8957-749c30e07233.png)


![image](https://user-images.githubusercontent.com/70655507/159129871-3b907e24-b252-4164-952d-303745d39fa7.png)

![image](https://user-images.githubusercontent.com/70655507/159130138-76a2670d-fe02-4964-8f50-f65edef6cc6c.png)

![image](https://user-images.githubusercontent.com/70655507/159130343-a96c4add-81c1-4554-85de-3b6c233c9922.png)

![image](https://user-images.githubusercontent.com/70655507/159130510-08c4474d-ae42-43a5-9cd9-e0965e004766.png)

![image](https://user-images.githubusercontent.com/70655507/159130558-a6ed727c-4b66-4cf1-a202-1be670ee2b68.png)

![image](https://user-images.githubusercontent.com/70655507/159130661-b3ddd4e1-fcca-449a-8256-44da5b9d0750.png)

![image](https://user-images.githubusercontent.com/70655507/159130916-48eed968-53e9-46df-b5d1-3f4ef6e50203.png)

![image](https://user-images.githubusercontent.com/70655507/159130952-3e9a687b-9476-4600-9d22-3c972d1df7ae.png)

![image](https://user-images.githubusercontent.com/70655507/159130973-4abe8614-1392-42be-86a9-18f7258b7891.png)

![image](https://user-images.githubusercontent.com/70655507/159130989-448ffbcf-54dd-4c3b-b971-551660f2124c.png)

![image](https://user-images.githubusercontent.com/70655507/159131029-9440074f-dd71-4259-b48e-1d23dd1b159a.png)

![image](https://user-images.githubusercontent.com/70655507/159131080-f5ec1461-2757-4378-90bd-bee735a6182d.png)

![image](https://user-images.githubusercontent.com/70655507/159131122-dd3d2c15-a8e8-4e19-b5f0-a8b984e15cad.png)

