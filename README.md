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

![erd](https://user-images.githubusercontent.com/108968316/193586316-cd539be4-810c-42af-aee3-d2dbe731ef37.jpg)


<h3>주요 기능</h3>

* 실시간 챗팅
* 가장 인기있는 랭킹 시스템


<h2>트러블 슈팅</h2>

<h3> build시 속도 저하</h3>
문제 상황 :
- 점점 프로젝트를 진행할수록 build 시에 시간이 늘어나는것으로 보여 
    
    이것을 해결하고자 했습니다.
해결 방안 : 
 
 gitaction에서 cache를 이용해서 처리하는 방법이 있다는 것을 알고 바로 적용을 해서
 ![cahsh_00000](https://user-images.githubusercontent.com/108968316/193587710-98c87516-7896-46b8-90a0-3ff7b821e685.jpg)
![cahsh_00001](https://user-images.githubusercontent.com/108968316/193587724-8d11feec-853e-4a85-951a-9876218a6a02.jpg)

위와 같은 식으로 약 20초의 속도를 감소시키는데에 성공했습니다.
 


<h3> 엔진엑스를 이용한 무중단 배포중 임베디드 레디스 이용</h3>

문제 상황 : 

- nginx를 이용해서 무중단 배포중에 임베디드 레디스를 사용해서 챗팅기능을 구현하는데

새로운 빌드 배포시에 새로운 서버가 배포가 안되는 문제 발생

해결 방안 :

- 임베디드 레디스가 작동중에는 새로운 서버를 띄울수 없는점을 발견했습니다.

그래서 새로운 서버가 배포 할 때 레디스도 같이 종료후 실행을 하는 것으로 처리했습니다.

![error2](https://user-images.githubusercontent.com/108968316/193588930-2af3c4ce-b680-4ed3-a1cf-36ee82c2e438.jpg)
![error](https://user-images.githubusercontent.com/108968316/193588936-c08cdcff-49a7-4954-959f-201c92356ccf.jpg)



<h2>API</h2>

https://www.notion.so/API-1a8bdd074c034c799550baed8f6caa7b
