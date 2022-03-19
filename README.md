# REST API 배포 프로젝트

Spring boot 와 REST API 를 활용하여 CRUD 를 설계 및 Docker & Travis 로 AWS 에 "자동" 배포하는 프로젝트입니다.

주제: REST API & 자동배포

개발인원: 개인

개발기간: 2022년 3월 14일 ~ 2022년 3월

툴
- 스프링부트: 2.2.7.RELEASE
- 빌드도구: Maven
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

