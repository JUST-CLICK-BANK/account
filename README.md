# 프로젝트 소개
Click이라는 누구나 쉽게 사용할 수 있는 금융 앱 애플리케이션 프로젝트입니다.

# 맡은 역할
계좌 및 이체 서비스를 담당하였고 계좌는 일반 계좌, 모임 통장 계좌, 적금 계좌를 구현하였습니다.

이체는 범용적 API로 이체, Pay, 카드 결제, 친구 송금 서비스에서 사용할 수 있도록 구현하였습니다.

# 기술 스택
- Server
  
![Spring-Boot](https://img.shields.io/badge/spring--boot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)
- Database
  
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

- Infra
  
![Google Cloud](https://img.shields.io/badge/GoogleCloud-%234285F4.svg?style=for-the-badge&logo=google-cloud&logoColor=white)
![Kubernetes](https://img.shields.io/badge/kubernetes-%23326ce5.svg?style=for-the-badge&logo=kubernetes&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
- Ci/CD
  
![Jenkins](https://img.shields.io/badge/jenkins-red.svg?style=for-the-badge&logo=jenkins&logoColor=white)

# ERD
![image](https://github.com/user-attachments/assets/18b5990d-4dd4-4e76-9fbc-a2b57a3327a7)


# Data Flow
![image](https://github.com/user-attachments/assets/4818f3a3-4c81-4255-a1d8-e6b4bf6d9e75)

# Trouble Shooting
임의의 난수로 계좌를 생성하는 로직을 작성하였습니다.
생성된 계좌번호가 중복될 수 있는 문제를 발견하여, 중복될 경우 다시 생성하여 계좌가 존재하지 않을때, Account 테이블에 저장하는 로직을 작성하였습니다.
생성된 계좌번호가 Account 테이블을 비교하며 탐색하므로 데이터가 많을수록 시간이 많이 소모된다는 문제가 있습니다.
이는 계좌 번호를 생성할 때 중복된 계좌가 존재할 경우, 해당 계좌에 이미 돈이 있어서 자원이 공유되는 문제가 발생할 수 있기 때문이며, 이를 방지하기 위해 계좌를 전체 탐색할 수밖에 없었습니다.

테이블 설계 시 친구가 모임 통장에 수락할 경우 계좌가 생성되는 로직을 구현하였습니다.
문제점은 모임 통장에서 친구 수락 시 계좌 테이블에 데이터가 많이 쌓인다는 것입니다.
이를 해결하기 위해, 계좌 테이블과 유저 테이블 사이에 모임 통장 테이블이 존재하는 식으로 설계를 하여 문제를 해결하였습니다. 









