<div style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/e4f51055-71d2-451b-babb-17fb199a9995" width="30%" height="30%" />

</div>


# 여행계획 생성 및 공유 서비스(Kt-ravel)

Kt-ravel은 다양한 사용자들이 여행 계획을 생성하고 공유할 수 있는 MSA 기반의 플랫폼입니다.   
<br/>
사용자는 각자 다른 계획을 만들고, 다른 사용자의 계획을 팔로우하며, 크레딧 시스템을 통해 활동할 수 있습니다.

<br/>
<br/>
<br/>

## 목차

**I. [서비스 시나리오](#서비스-시나리오)**    
 - [기능적 요구사항](#기능적-요구사항functional-requirements)
 - [비기능적 요구사항](#비기능적-요구사항-non-functional-requirements)

**II. [체크포인트](#체크포인트)**    
 - [아키텍처 설계(IaaS)](#아키텍처-설계iaas)
 - [Data Modeling/서비스 분리/설계 역량(BIZ)](#data-modeling서비스-분리설계-역량biz-15점)
 - [MSA개발 또는 개발관리 역량](#msa개발-또는-개발관리-역량-20점)
 - [클라우드 배포 역량](#클라우드-배포-역량-20점)

**III. [아키텍처 설계(IaaS)](#아키텍처-설계iaas))**    
 - [아키텍처](#아키텍처)
 - [데이터](#데이터)
 - [API](#API)
 - [보안](#보안)
 - [확장성 고려](#확장성-고려)

**IIII. [Data Modeling/서비스 분리/설계(BIZ)](#data-modeling서비스-분리설계biz)**    
 - [MSAEZ 모델링(Event Storming 결과)](#msaezi-모델링event-storming-결과)
 - [헥사고날 아키텍처 다이어그램 도출](#헥사고날-아키텍처-다이어그램-도출)

**V. [MSA개발 및 개발관리](#msag개발-및-개발관리)**    
 - [개발 환경 구축](#개발-환경-구축)
 - [백엔드 마이크로서비스 개발](#백엔드-마이크로서비스-개발)
 - [프론트엔드](#프론트엔드)

**VI. [클라우드 배포](#클라우드-배포)**    

<br/>
<br/>
<br/>
  
## I. 서비스 시나리오

### 기능적 요구사항(Functional Requirements)

### 1. 회원 가입(Sign up) 및 토큰(Token) 관리   
   **1.1 회원 가입 및 로그인**  
   - 사용자는 구글 또는 네이버 계정을 통해 회원가입 및 로그인이 가능해야 한다.

   **1.2 토큰(크레딧)**  
   - 사용자는 충전/결제 시스템을 통해 토큰을 획득할 수 있어야 한다.
   - 토큰은 여행 계획 추천 기능에 사용될 수 있어야 한다.

### 2. 여행 계획(Plan) 생성/수정/삭제, 추천(Recommendation)     
   **2.1 여행 계획 생성**  
   - 사용자는 목적지(location), 날짜(travelDate), 예산(budget), 인원수(groupSize), 세부 활동(details) 등의 상세 내용을 포함한 여행 계획을 생성할 수 있어야 한다.
   - 목적지(lacation), 날짜(travelDate), 인원수(groupSize)는 필수적으로 입력되야 한다.
   
   **2.2 여행 계획 수정**    
   - 사용자는 이미 생성한 여행 계획을 수정할 수 있어야 한다.

   **2.3 여행 계획 삭제**    
   - 사용자는 이미 생성한 여행 계획을 삭제할 수 있어야 한다.

   **2.4 여행 계획 공유**    
   - 계획은 별도의 대시보드에서 공유되어 확인할 수 있어야 한다. 
  
   **2.5 사용자 맞춤형 추천(A type)**  
   - 사용자의 개인적인 계획에 따라 맞춤형 여행 계획을 추천할 수 있어야 한다.
   - 필수 입력 값: 목적지(location), 날짜(travelDate), 예산(budget), 인원수(groupSize)
   - 선택 입력 값: 세부 활동(details)
   
   **2.6 트랜드 기반 추천(B type)**  **🚧<span style="color: blue;">미구현🚧</span>**
   - 팔로우 수가 많은 상위 사용자의 여행 계획에 따라, 여행 계획을 추천할 수 있어야 한다.
   - 사용자의 팔로우 여부와 관련 없이 동작해야 한다.
   - 필수 입력 값: 목적지(location), 날짜(travelDate), 인원수(groupSize)

   **2.7 사용자 취향 기반 추천(C type)** **🚧<span style="color: blue;">미구현🚧</span>**
   - 사용자가 '좋아요(하트)'를 누른 계획을 기반으로 여행 계획을 추천할 수 있어야 한다.
   - 해당 계획들의 '세부 활동(details)'을 기반으로 계획이 추천되어야 한다. 

   **2.8 추천 여행 계획 저장**
   - 각 추천에는 100개의 토큰이 소모되어야 한다.
   - 추천된 모든 계획은 별도의 대시보드에서 확인 가능해야 한다. 

### 3. 사용자 팔로우/언팔로우(Follow/Unfollow)    
   **3.1 팔로우 기능**  
   - 사용자는 다른 사용자를 팔로우할 수 있다.
   
   **3.2 언팔로우 기능**  
   - 사용자는 더 이상 관심이 없는 사용자를 언팔로우할 수 있다.
   
   **3.3 팔로우 대시보드** 
   - 팔로우한 사용자 목록은 별도 대시보드에서 확인할 수 있어야 한다.

### 4. 여행 계획 좋아요(Like)
   **4.1 좋아요 기능**
   - 사용자는 선호하는 여행 계획에 대해 '좋아요(하트)'를 표시할 수 있다.

   **4.2 좋아요 취소 기능**
   - 사용자는 더 이상 선호하지 않는 여행 계획의 '좋아요(하트)'를 취소할 수 있다.

   **4.3 좋아요 대시보드**
   - 사용자는 '좋아요(하트)'를 표시한 여행 계획을 별도의 대시보드에서 확인할 수 있어야 한다. 

### 5. 알림(Notification) 기능    
   **5.1 계획 생성 알림**  
   - 팔로우한 사용자가 계획을 생성할 경우, 팔로워에게 시스템에서 알림을 발송한다.
   
   **5.2 팔로우 알림**  
   - 사용자가 다른 사용자를 팔로우할 경우, 다른 사용자에게 시스템에서 알림을 발송한다.

   **5.3 좋아요(하트) 알림**
   - 다른 사용자가 사용자의 여행 계획을 좋아요(하트)할 경우, 사용자에게 시스템에서 알림을 발송한다.
   
<br/>
  
### 비기능적 요구사항 (Non-functional Requirements)

### 1. 트랜잭션 일관성 유지    

   **1.1 데이터 일관성**  
   - 사용자 토큰 사용, 여행 계획 생성/수정/삭제 등의 트랜잭션이 중단되지 않고 일관성을 유지해야 한다.
   - 분산 트랜잭션 처리 메커니즘을 통해 이를 보장한다.
   
   **1.2 ACID 속성 보장**  
   - 중요한 데이터(회원 정보, 여행 계획, 토큰 내역 등)에 대해 ACID 속성을 보장하는 데이터베이스 구조를 구현한다.

### 2. 서비스 간 느슨한 결합    

   **2.1 MSA**  
   - 각 기능이 독립된 마이크로서비스로 운영되며, 서로 간의 결합도를 최소화하여 유지보수 및 확장성을 높여야 한다.
   
   **2.2 API 기반 통신**  
   - 마이크로서비스 간의 통신은 RESTful API 기반으로 이루어져야 하며, 서비스의 장애나 업데이트가 다른 서비스에 영향을 미치지 않도록 설계해야 한다.

### 3. 장애 격리    

   **3.1 장애 감지 및 복구**  
   - 특정 서비스가 실패하더라도 전체 시스템이 영향을 받지 않도록 장애 격리 메커니즘을 구현해야 한다.
   
   **3.2 로그 관리**  
   - 장애를 감지하고, 이를 분석하여 빠르게 대응할 수 있는 로그 관리 시스템을 구현해야 한다.

### 4. 확장성 및 유연성    

   **4.1 자동 확장**  
   - 사용자 수 증가에 따라 자동으로 서버 리소스를 확장할 수 있는 기능을 갖추어야 한다.
   - 이를 위해 컨테이너화된 서비스나 클라우드 인프라를 사용할 수 있다.
   
   **4.2 유연한 데이터베이스 확장**  
   - 데이터베이스는 수평적 확장을 지원하며, 많은 양의 사용자 데이터를 효율적으로 처리할 수 있어야 한다.
   
   **4.3 플랫폼 확장**  
   - 새로운 기능이 추가되더라도 기존 시스템에 영향을 주지 않고 손쉽게 확장할 수 있는 유연한 아키텍처를 적용해야 한다.

### 5. 보안 및 개인정보 보호    

   **5.1 데이터 암호화**  
   - 사용자 정보 및 민감한 데이터는 전송 시 암호화해야 한다.
   
   **5.2 접근 제어**  
   - 각 서비스에 대한 접근 권한을 엄격히 관리하며, 사용자 인증 및 권한 부여를 통해 보호해야 한다.
   
   **5.3 개인정보 보호**  
   - 사용자의 여행 계획과 같은 개인정보는 외부에 노출되지 않도록 정책을 설정하고 준수해야 한다.

<br/>
<br/>
<br/>
  
## II. 체크포인트

### 1. 아키텍처 설계(IaaS) (15점)
  - 클라우드 아키텍처 구성
  - MSA 아키텍처 구성
    - 각 마이크로서비스가 독립적으로 배포 및 운영될 수 있도록 설계되었는가?
    - 각 서비스의 기능 및 책임이 명확히 분리되어 있는가?

### 2. Data Modeling/서비스 분리/설계 역량(BIZ) (15점) 
  - 도메인분석-이벤트스토밍
    - MSA분석/설계 DDD기반의 이벤트스토밍 및 분석 도구를 통하여 구체적인 내용으로 도메인이 분석되고 구현 가능한 방법으로 설계 되었는가?
    - 각 도메인 이벤트가 의미있는 수준으로 정의 되었는가? 

### 3. MSA개발 또는 개발관리 역량 (20점)
  - 분산트랜잭션-Saga
  - 보상처리-Compensation
  - 단일 진입점-Gateway
  - 분산 데이터 프로젝션
    - 각 로컬 트랜잭션이 순차적으로 실행되는지, 트랜잭션 간의 흐름이 적절하게 관리되는가?
    - 트랜잭션 실패 시 실행되는 보상 트랜잭션이 명확하게 정의 되어 있는가?
    - Gateway가 정상적으로 동작하여 트래픽 제어를 할 수 있는가?
    - 분산된 시스템 간 데이터 일관성을 유지하기 위한 동기화 전략이 적절한가(CQRS)?

### 4. 클라우드 배포 역량 (20점)
  - Docker Build 이미지 생성
  - Docker 이미지 배포(Docker Hub 사용)
  - K8S 배포(Azure)
    - Docker 이미지가 최적화되었는가?
    - Docker Hub로 이미지 배포를 하였는가?
    - Kubernetes 배포가 정상적으로 되었는가?

<br/>
<br/>
<br/>
  
## III. 아키텍처 설계(IaaS)

### 1. 아키텍처     
   - MSA 기반 설계:
     - Spring Boot 마이크로서비스
     - Vue.js 프론트엔드
   - Azure 클라우드 서비스 활용:
     - Azure Kubernetes Service (AKS)를 기반으로 한 컨테이너 오케스트레이션
     - Azure API Management를 활용한 API Gateway 구현
     - @@@@@ Elastic Load Balancer
     - @@@@@ Auto Scaling
     - @@@@@ VPC
    
### 2. 데이터     
   - 서비스별 독립 데이터베이스 설계: Spring Boot H2 메모리 DB
   - JPA와 Hibernate를 활용한 ORM 구현

### 3. API
   - RESTful API 설계 원칙 준수
   - Spring Boot 기반 API 엔드포인트 정의

### 4. 보안
   - OAuth 2.0 기반 소셜 로그인 (Google, Naver) 구현

### 5. 확장성 고려
   - @@@@@ Azure Cache for Redis를 활용한 분산 캐싱 전략
   - @@@@@ Azure Service Bus를 이용한 비동기 메시징 시스템 설계

<br/>
<br/>
<br/>
  
## IIII. Data Modeling/서비스 분리/설계(BIZ)

### 1. MSAEZ 모델링(Event Storming 결과)
https://dev.msaez.io/#/142835195/storming/travel

   **1.1 Evnet 도출**    
   
<img src="https://github.com/KT-HOO/KTravel/blob/main/img/0911_01.png" width="80%"/>    

    - 여행계획 생성 및 공유 서비스에 적합한 이벤트 도출
    - 서비스에 적합하지 않은 이벤트 삭제

   **1.2 Actor 식별, Command 부착**    
   
<img src="https://github.com/KT-HOO/KTravel/blob/main/img/0911_02.png" width="100%"/>    

    - 서비스의 주요 액터로 'member' 식별
    - 각 서비스 영역에 해당하는 커맨드를 부착
    
   **1.3 Aggregate 으로 묶기**    
   
<img src="https://github.com/KT-HOO/KTravel/blob/main/img/0911_03.png" width="100%"/>  

    - 연관된 엔터티와 이벤트를 묶어 어그리게잇 형성
    - 총 5개(Member, Plan, Follow, Like, Notofication)의 어그리게잇 도출

   **1.4 Domain 서열 분리, Bounded Context로 묶기**    
   
<img src="https://github.com/KT-HOO/KTravel/blob/main/img/0911_04.png" width="100%"/>  
  
(1) Core Domain: 비즈니스의 핵심 가치를 제공하는 영역      
  - member    
    - 사용자 관리와 인증을 담당    
    - 서비스의 근간이 되므로 연간 Up-time SLA 수준을 99.99% 목표    
    - 보안과 안정성이 중요하여 배포주기는 한 달에 1회로 제한하며, 철저한 테스트 후 배포    
     
  - plan    
    - 여행 계획 생성 및 관리를 담당    
    - 연간 Up-time SLA 수준 99.95% 목표    
    - 새로운 기능과 개선사항을 신속히 반영하기 위해 2주에 1회 배포를 목표    
     
(2) Supporting Domain: Core Domain을 지원하고 보완하는 영역    
  - follow    
    - 사용자 간 연결을 지원하는 보조 서비스    
    - 연간 Up-time SLA 수준 99.90% 목표    
    - 배포주기는 한 달에 1-2회로 유연하게 운영    
	    
  - like     
    - 사용자 상호작용을 증진시키는 보조 서비스      
    - 연간 Up-time SLA 수준 99.90% 목표    
    - 배포주기는 한 달에 1-2회로 유연하게 운영     
	    
(3) General Domain: 비즈니스에 필요하지만, 일반적인 기능만을 담당하는 영역    
  - notification    
    - 다른 서비스들의 이벤트를 기반으로 알림을 처리하는 서비스
    - 연간 Up-time SLA 수준 99.50% 목표
    - 배포주기는 필요에 따라 유연하게 조정하되 보통 한 달에 1회 정도로 운영

<br/>
   
   **1.5 Policy, Read Model 부착 및 컨택스트 매핑 (점선은 Pub/Sub, 실선은 Req/Resp)**     
   
<img src="https://github.com/KT-HOO/KTravel/blob/main/img/0911_05.png" width="100%"/>

    - 비즈니스 처리 로직에 따라 폴리시를 배치
    - 통신은 이벤트 기반의 비동기 방식(Pub/Sub)으로 설계
    - 'notification'이 다른 서비스의 데이터를 빠르게 수집하여 활용할 수 있도록 리드모델 배치

   **1.6 완성된 1차 모형에 대한 기능적 요구사항 검증**

<img src="https://github.com/KT-HOO/KTravel/blob/main/img/0911_06.png" width="100%"/>

    - 사용자는 구글 또는 네이버 계정을 통해 회원가입 및 로그인이 가능해야 한다. 🆗
    - 사용자는 충전/결제 시스템을 통해 토큰을 획득할 수 있어야 한다. 🆗
    - 토큰은 여행 계획 추천 기능에 사용될 수 있어야 한다. 🆗
    - 사용자는 목적지(location), 날짜(travelDate), 예산(budget), 인원수(groupSize), 세부 활동(details) 등의 상세 내용을 포함한 여행 계획을 생성할 수 있어야 한다. 🆗
    - 목적지(lacation), 날짜(travelDate), 인원수(groupSize)는 필수적으로 입력되야 한다. ❎
    - 사용자는 이미 생성한 여행 계획을 수정할 수 있어야 한다. 🆗
    - 사용자는 이미 생성한 여행 계획을 삭제할 수 있어야 한다. 🆗
    - 계획은 별도의 대시보드에서 공유되어 확인할 수 있어야 한다. ❎
    - 사용자의 개인적인 계획에 따라 맞춤형 여행 계획을 추천할 수 있어야 한다. 🆗
    - 팔로우 수가 많은 상위 사용자의 여행 계획에 따라, 여행 계획을 추천할 수 있어야 한다. ❎
    - 사용자가 '좋아요(하트)'를 누른 계획을 기반으로 여행 계획을 추천할 수 있어야 한다. ❎
    - 각 추천에는 100개의 토큰이 소모되어야 한다. ❎
    - 추천된 모든 계획은 별도의 대시보드에서 확인 가능해야 한다. ❎
    - 사용자는 다른 사용자를 팔로우할 수 있다. 🆗
    - 사용자는 더 이상 관심이 없는 사용자를 언팔로우할 수 있다. 🆗
    - 팔로우한 사용자 목록은 별도 대시보드에서 확인할 수 있어야 한다. ❎
    - 사용자는 선호하는 여행 계획에 대해 '좋아요(하트)'를 표시할 수 있다. 🆗
    - 사용자는 더 이상 선호하지 않는 여행 계획의 '좋아요(하트)'를 취소할 수 있다. 🆗
    - 사용자는 '좋아요(하트)'를 표시한 여행 계획을 별도의 대시보드에서 확인할 수 있어야 한다. ❎
    - 팔로우한 사용자가 계획을 생성할 경우, 팔로워에게 시스템에서 알림을 발송한다. ❌
    - 사용자가 다른 사용자를 팔로우할 경우, 다른 사용자에게 시스템에서 알림을 발송한다. 🆗
    - 다른 사용자가 사용자의 여행 계획을 좋아요(하트)할 경우, 사용자에게 시스템에서 알림을 발송한다. 🆗

   **1.7 완성된 1차 모형에 대한 비기능적 요구사항 검증**

<img src="https://github.com/KT-HOO/KTravel/blob/main/img/0911_06.png" width="100%"/>

    - 사용자 토큰 사용, 여행 계획 생성/수정/삭제 등의 트랜잭션이 중단되지 않고 일관성을 유지해야 한다. 🆗
    - 분산 트랜잭션 처리 메커니즘을 통해 이를 보장한다. 🆗
    - 중요한 데이터(회원 정보, 여행 계획, 토큰 내역 등)에 대해 ACID 속성을 보장하는 데이터베이스 구조를 구현한다. 🆗
    - 각 기능이 독립된 마이크로서비스로 운영되며, 서로 간의 결합도를 최소화하여 유지보수 및 확장성을 높여야 한다. 🆗
    - 마이크로서비스 간의 통신은 RESTful API 기반으로 이루어져야 하며, 서비스의 장애나 업데이트가 다른 서비스에 영향을 미치지 않도록 설계해야 한다. 🆗
    - 특정 서비스가 실패하더라도 전체 시스템이 영향을 받지 않도록 장애 격리 메커니즘을 구현해야 한다. 🆗
    - 장애를 감지하고, 이를 분석하여 빠르게 대응할 수 있는 로그 관리 시스템을 구현해야 한다. 🆗
    - 사용자 수 증가에 따라 자동으로 서버 리소스를 확장할 수 있는 기능을 갖추어야 한다. 🆗
    - 이를 위해 컨테이너화된 서비스나 클라우드 인프라를 사용할 수 있다. 🆗
    - 데이터베이스는 수평적 확장을 지원하며, 많은 양의 사용자 데이터를 효율적으로 처리할 수 있어야 한다. 🆗
    - 새로운 기능이 추가되더라도 기존 시스템에 영향을 주지 않고 손쉽게 확장할 수 있는 유연한 아키텍처를 적용해야 한다. 🆗
    - 사용자 정보 및 민감한 데이터는 전송 시 암호화해야 한다. 🆗
    - 각 서비스에 대한 접근 권한을 엄격히 관리하며, 사용자 인증 및 권한 부여를 통해 보호해야 한다. 🆗
    - 사용자의 여행 계획과 같은 개인정보는 외부에 노출되지 않도록 정책을 설정하고 준수해야 한다. 🆗

   **1.8 모형 수정 및 최종 모형 도출**    
   
<img src="https://github.com/KT-HOO/KTravel/blob/main/img/0911_05.png" width="100%"/>

    -  알림(Notification) 기능-팔로우 알림
      - 팔로우한 사용자가 계획을 생성할 경우, 팔로워에게 시스템에서 알림을 발송한다. ❌
      - 'Follow' 리드모델을 'notification'에 추가
    - 수정한 모형으로 최종 모형 도출

<br/>

### 2. 헥사고날 아키텍처 다이어그램 도출
<img src="https://github.com/KT-HOO/KTravel/blob/main/img/0911_kafka.png" width="100%"/>

    - 각 서비스는 Kafka Publisher와 Kafka Listener를 통해 이벤트를 송신하고 수신 (느슨한 결합 및 확장성 확보)
    - 'Plan' 서비스는 Kafka 외에 REST 기반 통신을 사용하여 다른 시스템과 데이터를 주고 받음
    - 호출 관계에서 Pub/Sub과 Req/Resp 구분
    - JPA를 통해 각 서비스는 각각의 데이터베이스(H2)를 관리

<br/>
<br/>
<br/>
  
## V. MSA개발 및 개발관리

- 분산 트랜잭션 (Saga 패턴):
  - Kt-ravel에서 각 마이크로서비스 간의 트랜잭션을 분산 처리할 수 있는 Saga 패턴을 구현해야 합니다. 예를 들어, 여행 계획을 생성하는 과정에서 여러 서비스가 연동되는 경우, 트랜잭션 실패 시 보상 트랜잭션(Compensation)이 필요합니다.
- 보상 처리 (Compensation):
  - 여행 계획이 실패하거나 수정할 경우 이를 원상 복구하는 보상 트랜잭션을 명확히 정의합니다. 보상 트랜잭션은 트랜잭션 흐름 중 오류가 발생할 때 실행됩니다.
- 단일 진입점 (Gateway):
  - 서비스의 트래픽 제어를 위해 API Gateway를 설정하여, 모든 클라이언트 요청이 단일 진입점을 통해 각 서비스로 라우팅될 수 있게 합니다. 이를 통해 인증 및 부하 분산을 효율적으로 처리합니다.
- 분산 데이터 프로젝션:
  - CQRS(Command Query Responsibility Segregation) 패턴을 도입해, 각 서비스의 데이터를 분산하여 저장하고, 읽기/쓰기 작업을 분리하여 데이터 일관성을 유지합니다.


### 1. 개발 환경 구축    
   - Spring Boot 개발 환경 설정
   - Vue.js 프로젝트 구조 설정 (Vue CLI 활용)
   - Docker를 이용한 컨테이너화 환경 구성
   - @@@@@ Azure DevOps를 활용한 CI/CD 파이프라인 구축

### 2. 백엔드 마이크로서비스 개발
이전 단계에서 도출된 OOOOO 아키텍처에 따라, 각 BC별로 대변되는 마이크로 서비스들을 Spring Boot로 구현하였다.    
구현한 각 서비스를 로컬에서 실행하는 방법은 아래와 같다 (각자의 포트넘버는 8081 ~ 808n 이다)

```
   mvn spring-boot:run
```  
   
   **2.1 CQRS**  
   - 예시 숙소(Room) 의 사용가능 여부, 리뷰 및 예약/결재 등 총 Status 에 대하여 고객(Customer)이 조회 할 수 있도록 CQRS 로 구현하였다.
     - 예시 room, review, reservation, payment 개별 Aggregate Status 를 통합 조회하여 성능 Issue 를 사전에 예방할 수 있다.
     - 예시 비동기식으로 처리되어 발행된 이벤트 기반 Kafka 를 통해 수신/처리 되어 별도 Table 에 관리한다
     - 예시 Table 모델링 (ROOMVIEW)

  ![image](https://user-images.githubusercontent.com/77129832/119319352-4b198c00-bcb5-11eb-93bc-ff0657feeb9f.png)
     - viewpage MSA ViewHandler 를 통해 구현 ("RoomRegistered" 이벤트 발생 시, Pub/Sub 기반으로 별도 Roomview 테이블에 저장)

   **2.2 API 게이트웨이**
      1. gateway 스프링부트 App을 추가 후 application.yaml내에 각 마이크로 서비스의 routes 를 추가하고 gateway 서버의 포트를 8080 으로 설정
       
          - application.yaml 예시
            ```
            spring:
              profiles: docker
              cloud:
                gateway:
                  routes:
                    - id: payment
                      uri: http://payment:8080
                      predicates:
                        - Path=/payments/** 
                    - id: room
                      uri: http://room:8080
                      predicates:
                        - Path=/rooms/**, /reviews/**, /check/**
                    - id: reservation

            ```

      2. Kubernetes용 Deployment.yaml 을 작성하고 Kubernetes에 Deploy를 생성함
          - Deployment.yaml 예시
          

            ```
            apiVersion: apps/v1
            kind: Deployment
            metadata:
              name: gateway
              namespace: airbnb
              labels:
                app: gateway
            spec:
              replicas: 1
              selector:
                matchLabels:
                  app: gateway
            
![image](https://user-images.githubusercontent.com/80744273/119321943-1d821200-bcb8-11eb-98d7-bf8def9ebf80.png)
	    

### 3. 프론트엔드
-  

<br/>
<br/>
<br/>
  
## VI. 클라우드(Azure) 배포

- Azure 리소스 그룹 및 AKS(Azure Kubernetes Service) 클러스터 생성<br/>
  <img width="70%" alt="image" src="https://github.com/user-attachments/assets/248daa26-093c-4e3f-9e66-491a66a458e5"><br/>
  <img width="70%" alt="image" src="https://github.com/user-attachments/assets/7c1f886d-fb3b-47d2-bc7b-9c17c68ba96f"><br/>
  <img width="70%" alt="image" src="https://github.com/user-attachments/assets/292f5c03-5c6b-46b1-b62a-74658961f969"><br/>
  <br/>

- 각각의 서비스 별(bounded context) maven 빌드 후 jar 패키징파일 생성<br/>
  <img width="70%" alt="image" src="https://github.com/user-attachments/assets/e4996ab2-6b49-4f02-b2f3-2fa342cd46a4">
  <img width="70%" alt="image" src="https://github.com/user-attachments/assets/4155affa-4b94-4b74-b4da-084481418636">
  ```
  (main) $ cd follow
  (main) $ mvn package -B -Dmaven.test.skip=true
  ```
  <br/>
  
- 패키징한 jar 파일을 통한 docker image build 및 docker hub에 push<br/>
  <img width="70%" alt="image" src="https://github.com/user-attachments/assets/ec43afdc-b8e7-4f72-8527-253235f35751">
  <img width="70%" alt="image" src="https://github.com/user-attachments/assets/ec7385cb-9adb-48f9-ba34-70f812f48d74">
  <img width="70%" alt="image" src="https://github.com/user-attachments/assets/3a3f0480-b320-494b-af9e-805213b89f00">
  ```
  (main) $ docker login
  (main) $ docker build -t gbgb45/follow:240912 .
  (main) $ docker push gbgb45/follow:240912
  ```
  <br/>

- Helm 패키지를 통한 클러스터에 Event Store(kafka) 설치<br/>
  <img width="70%" alt="image" src="https://github.com/user-attachments/assets/1e610341-c7ff-48f1-9507-070426163f66">
  <img width="70%" alt="image" src="https://github.com/user-attachments/assets/da8d5252-42a8-4fa2-8af6-6494aedb4b1b">
  <img width="70%" alt="image" src="https://github.com/user-attachments/assets/f2291260-be63-4306-a691-ea312873335e">
  ```
  (main) $ helm repo add bitnami https://charts.bitnami.com/bitnami
  (main) $ helm repo update
  (main) $ helm install my-kafka bitnami/kafka –verison 23.0.5
  ```
  <br/>

- 각각의 서비스 및 게이트웨이의 deployment.yaml, service.yaml파일을 통한 배포<br/>
  <img width="55%" alt="image" src="https://github.com/user-attachments/assets/b7cb17c6-44ae-49ab-a668-125d9708f5fb">
  <img width="70%" alt="image" src="https://github.com/user-attachments/assets/bf46bc28-031e-4fbb-98b3-9f866dd7faf2">
  <img width="70%" alt="image" src="https://github.com/user-attachments/assets/c7cd32fc-4c70-4696-a008-4f15b6679e30">
  ```
  (main) $ kubectl apply -f kubernetes/deployment.yaml
  (main) $ kubectl apply -f kubernetes/service.yaml
  ```
  <br/>





