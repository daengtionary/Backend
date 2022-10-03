# ✨댕과사전 backend

![KakaoTalk_20221003_192709995](https://user-images.githubusercontent.com/108968316/193556299-2f97f1b0-31cb-4c8e-9bd2-125f4d9802b5.jpg)


<h2>✏프로젝트 소개</h2>
댕과사전

안녕하세요 행해99기 8기 댕과사전 서비스를 만든 3조 입니다.

저희 서비스는 반려견을 키우면서 병원/호텔 등등의 정보를 공유하며 얻을 수 있으며

커뮤니티 서비스와 챗팅 서비스를 이용해서 친목을 쌓으며 마켓 기능을 통하여 

판매하고 싶은 상품도 판매 할 수 있는 서비스입니다.

<h2>프로젝트 노션</h2>

https://www.notion.so/b16810b040254299a360deec190d1f4f

<h2>📋프로젝트 기간</h2>

2022-08-26 ~ 2022-10-07

서비스 시작 일

2022-10-03

<h2>📊팀원 소개</h2>

|이름|github|position|
|------|---|---|
|박진우|https://github.com/Jinu0729|back-end|
|안승현|https://github.com/zemiles|back-end|
|한동훈|https://github.com/hdonghun|back-end|

<h2>📄기술 스택</h2>

<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"><img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"><img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white"><img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"><img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white">


<h2>툴</h2>

<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"><img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white"><img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white"><img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white"><img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white"><img src="https://img.shields.io/badge/IntelliJ IDEA-000000?style=for-the-badge&logo=IntelliJ IDEA&logoColor=white"><img src="https://img.shields.io/badge/GitKraken-179287?style=for-the-badge&logo=GitKraken&logoColor=white"><img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white"><img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white"><img src="https://img.shields.io/badge/Code Deploy-007396?style=for-the-badge&logo=Code Deploy&logoColor=white">


<h2>서버</h2>

<img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white"><img src="https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white">

<h2>API</h2>

<h3>서비스 아키텍쳐</h3>

![main](https://user-images.githubusercontent.com/108968316/193571757-7770d7e8-bab7-4b7e-bdc7-961a273c0214.jpg)

<h3>ERD</h3>

![erd](https://user-images.githubusercontent.com/108968316/193579678-75d3d11a-4500-4e67-a17c-12e385bdc955.jpg)


<h3>주요 기능</h3>

* 실시간 챗팅
* 가장 인기있는 랭킹 시스템


<h2>트러블 슈팅</h2>



# 배포시 서버 구동 멈춤 문제

문제 상황 : 

- ci/cd 를 진행 할때 백엔드에 ec2가 약 5분 내로 멈추는 경우가 발생해서

       무중단 배포를 도입해야 될거 같다고 의견을 통합했습니다.

해결 방안 :

- 무중단 배포의 핵심은 로드밸런서(Load Balancer)를 통해 연결된 두 개 이상의 (서로 다른 IP, 포트를 가진) 인스턴스에 트래픽을 제어해 배포하는 것 이라는 결론에 도달
- 엔진엑스/도커 가 후보에 올랐습니다.

의견조율

- 기존에 ci/cd를 도커로 이용했기에 도커를 이용해서 처리 하기로 판단

해결 

- 도커를 이용해서 짧은 시간내에 무중단 배포를 처리하기에는 어렵다고 판단.
- ec2 내부에 nginx를 설치/설정 하여 gitaction / codedeploy / s3 를 이용해서 처리 완료


<h2>API</h2>

https://www.notion.so/API-1a8bdd074c034c799550baed8f6caa7b
