## 프로젝트 기본 설정

<details>
<summary>Getting Started</summary>

### Prerequisites
#### Running Docker Containers
`local` profile 로 실행하기 위하여 인프라가 설정되어 있는 Docker 컨테이너를 실행해주셔야 합니다.

```bash
docker-compose up -d
```
</details>

---

# 이커머스 시스템 설계 및 사용자 요구사항 분석
## 목차
0. [프로젝트 마일스톤](#0-프로젝트-마일스톤)
1. [잔액 조회 API](#1-잔액-조회-api)
2. [잔액 충전 API](#2-잔액-충전-api)
3. [상품 조회 API](#3-상품-조회-api)
4. [상품 주문 API](#4-상품-주문-api)
5. [상품 결제 API](#5-상품-결제-api)
6. [인기 상품 조회 API](#6-인기-상품-조회-api)
7. [선착순 쿠폰 발급 API](#7-선착순-쿠폰-발급-api)
8. [보유 쿠폰 조회 API](#8-보유-쿠폰-조회-api)
---
## 0. 프로젝트 마일스톤
![milestone](/docs/milestone/milestone.png)
## 1. 잔액 조회 API
### 1.1. 시퀀스다이어그램
![1.잔액조회](/docs/sequnceDiagram/1.getBalance.png)

---
## 2. 잔액 충전 API
### 2.1. 시퀀스다이어그램
![2.잔액 충전](/docs/sequnceDiagram/2.chargeBalance.png)

---
## 3. 상품 조회 API
### 3.1. 시퀀스다이어그램
![3.상품 조회](/docs/sequnceDiagram/3.getItem.png)

---

## 4. 상품 주문 API
### 4.1. 시퀀스다이어그램
![4.상품 주문](/docs/sequnceDiagram/4.orderItem.png)

---

## 5. 상품 결제 API
### 5.1. 시퀀스다이어그램
![5.상품 결제](/docs/sequnceDiagram/5.payItem.png)

---

## 6. 인기 상품 조회 API
### 6.1. 시퀀스다이어그램
![6.인기 상품 조회](/docs/sequnceDiagram/6.getPopularItem.png)


---

## 7. 선착순 쿠폰 발급 API
### 7.1. 시퀀스다이어그램
![7.쿠폰 발급](/docs/sequnceDiagram/7.issueCoupon.png)



---

## 8. 보유 쿠폰 조회 API
### 8.1. 시퀀스다이어그램
![8.보유 쿠폰 조회](/docs/sequnceDiagram/8.getCoupon.png)

