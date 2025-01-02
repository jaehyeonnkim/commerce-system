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

# 이커머스 시스템 설계 
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
### 1.2. API 명세
#### 1.2.1. Request
- Endpoint: `GET /api/user/{userId}/balance`
- Content-Type: application/json

#### 1.2.2. Response
- HTTP Status: `200 OK`
- Content-Type: application/json
```json
{
  "userId": 123,
  "balance": 10000
}
```
#### 1.2.3. Error Response
1. 사용자값이 존재하지 않을 때
- HTTP Status: 404 Not Found
- Content-Type: application/json
```json
{
    "errorCode": "USER_NOT_FOUND",
    "message": "사용자 정보를 찾을 수 없습니다."
}
```
---
## 2. 잔액 충전 API
### 2.1. 시퀀스다이어그램
![2.잔액 충전](/docs/sequnceDiagram/2.chargeBalance.png)
### 2.2. API 명세
#### 2.2.1. Request
- Endpoing: `POST /api/user/{userId}/balance/charge`
- Content-Type: application/json
```json
{
  "amount": 5000
}
```
#### 2.2.2. Response
- HTTP Status: `201 Created`
- Content-Type: application/json
```json
  {
    "userId": 123,
    "balance": 15000,
    "message": "잔액이 성공적으로 충전되었습니다."
  }
 ```
#### 2.2.3. Error Response
1. 사용자값이 존재하지 않을 때
- HTTP Status: `404 Not Found`
- Content-Type: application/json
```json
{
    "errorCode": "USER_NOT_FOUND",
    "message": "사용자 정보를 찾을 수 없습니다."
}
```
2. 충전금액이 유효하지 않을 때 (ex.음수, 최대금액 초과, null 등)
- HTTP Status: `400 Bad Request`
- Content-Type: application/json
```json
{
    "errorCode": "INVALID_REQUEST",
    "message": "충전금액이 유효하지않습니다."
}
```
---
## 3. 상품 조회 API
### 3.1. 시퀀스다이어그램
![3.상품 조회](/docs/sequnceDiagram/3.getItem.png)
### 3.2. API 명세
#### 3.2.1. Request
- Endpoint: `GET /api/product?page=0&size=10&sort=price,asc`
- Content-Type: application/json
- Parameters

  |파라미터| 타입 | 설명                     | 필수   | default       |
        |--|----|------------------------|------|---------------|
  |page| int| 페이지                    | -    | 0             |
  |size| int| 페이지 당 상품 갯수            | -    | 20            |
  |sort| string | 정렬 기준(가격순, 최신순, 인기순 등) | -    | reg_date,desc |

#### 3.2.2. Response
- HTTP Status: `200 OK`
- Content-Type: application/json
```json
{
  "page": 1,
  "size": 10,
  "sort": "price,asc",
  "totalCount": 654,
  "productList": [
    {
      "productId": 23421,
      "name": "가방1",
      "price": 43000,
      "categoryName": "bag",
      "count" : 300,
      "createdAt" : "2024-03-02T03:10:00"
    },
    {
      "productId": 5421,
      "name": "가방2",
      "price": 41000,
      "categoryName": "bag",
      "count" : 112,
      "createdAt" : "2024-04-02T08:20:00"
    },
    ////
  ]
}
 ```
#### 3.2.3. Error Response
1. 페이지가 비어있을 때 (ex. 상품이 5개밖에 없는데 page=3을 요청)
- HTTP Status: `404 Not Found`
- Content-Type: application/json
```json
{
    "errorCode": "PRODUCT_NOT_FOUND",
    "message": "상품 정보를 찾을 수 없습니다."
}
```
2. 유효하지 않은 파라미터를 보냈을 떄 (ex.음수, 타입오류 등)
- HTTP Status: `400 Bad Request`
- Content-Type: application/json
```json
{
    "errorCode": "INVALID_REQUEST",
    "message": "요청 인자가 유효하지않습니다."
}
```
---

## 4. 상품 주문 API
### 4.1. 시퀀스다이어그램
![4.상품 주문](/docs/sequnceDiagram/4.orderItem.png)
### 4.2. API 명세
#### 4.2.1. Request
- Endpoint: `POST /api/{userId}/order`
- Content-Type: application/json
```json
{
  "productList": [
    {
      "productId": 1100,
      "quantity": 1
    },
    {
      "productId": 1200,
      "quantity": 1
    }
  ]
}
```
#### 4.2.2. Response
- HTTP Status: `201 Created`
- Content-Type: application/json
```json
{
    "orderId": 2149,
    "date": "2025-01-02T08:20:00",
    "totalAmount" : 58000,
    "message": "주문이 성공적으로 완료되었습니다."
}
 ```
#### 4.2.3. Error Response
1. 사용자값이 존재하지 않을 때
- HTTP Status: `404 Not Found`
- Content-Type: application/json
```json
{
    "errorCode": "USER_NOT_FOUND",
    "message": "사용자 정보를 찾을 수 없습니다."
}
```
2. 상품 정보를 찾을 수 없을 때
- HTTP Status: `404 Not Found`
- Content-Type: application/json
```json
{
    "errorCode": "ITEM_NOT_FOUND",
    "message": "상품 정보를 찾을 수 없습니다."
}
```
3. 재고가 없는 상품일 때
- HTTP Status: `404 Not Found`
- Content-Type: application/json
```json
{
    "errorCode": "ITEM_SOLD_OUT",
    "message": "판매가 중단되거나 품절된 상품입니다"
}
```
4. 유효하지 않은 파라미터를 보냈을 떄 (ex.음수, 타입오류 등)
- HTTP Status: 400 Bad Request
- Content-Type: application/json
```json
{
    "errorCode": "INVALID_REQUEST",
    "message": "요청 인자가 유효하지않습니다."
}
```   

---

## 5. 상품 결제 API
### 5.1. 시퀀스다이어그램
![5.상품 결제](/docs/sequnceDiagram/5.payItem.png)
### 5.2. API 명세
#### 5.2.1. Request
- Endpoint: `POST /api/pay/{userId}`
- Content-Type: application/json
```json
{
  "orderId": 2149,
  "couponId": 112,
  "type": "card"
}
```
#### 5.2.2. Response
- HTTP Status: `201 Created`
- Content-Type: application/json
```json
{
  "paymentId": 12345,
  "orderId": 2149,
  "couponId": 112,
  "status": "SUCCESS",
  "amount": 58000,
  "date": "2025-01-03T12:34:56Z"
}
 ```
#### 5.2.3. Error Response
1. 사용자값이 존재하지 않을 때
- HTTP Status: `404 Not Found`
- Content-Type: application/json
```json
{
    "errorCode": "USER_NOT_FOUND",
    "message": "사용자 정보를 찾을 수 없습니다."
}
```
2. 주문 정보를 찾을 수 없을 때
- HTTP Status: `404 Not Found`
- Content-Type: application/json
```json
{
    "errorCode": "ORDER_NOT_FOUND",
    "message": "주문 정보를 찾을 수 없습니다."
}
```
3. 유효하지 않은 파라미터를 보냈을 떄 (ex.음수, 타입오류 등)
- HTTP Status: 400 Bad Request
- Content-Type: application/json
```json
{
    "errorCode": "INVALID_REQUEST",
    "message": "요청 인자가 유효하지않습니다."
}
```   
4. 만료된 쿠폰을 선택했을 때
- HTTP Status: 400 Bad Request
- Content-Type: application/json
```json
{
    "errorCode": "EXPIRED_COUPON",
    "message": "유효기간이 만료된 쿠폰입니다."
}
```   

---

## 6. 인기 상품 조회 API
### 6.1. 시퀀스다이어그램
![6.인기 상품 조회](/docs/sequnceDiagram/6.getPopularItem.png)
### 6.2. API 명세
#### 6.2.1. Request
- Endpoint: `GET /api/product/popular`
- Content-Type: application/json

#### 6.2.2. Response
- HTTP Status: `200 OK`
- Content-Type: application/json
```json
[
  {
    "itemId": 2341,
    "totalQuantity": 93,
    "calculatedAt": "2025-01-03T00:00:00Z"
  },
  {
    "itemId": 1231,
    "totalQuantity": 75,
    "calculatedAt": "2025-01-03T00:00:00Z"
  },
  {
    "itemId": 1145,
    "totalQuantity": 43,
    "calculatedAt": "2025-01-03T00:00:00Z"
  }
]
 ```
#### 6.2.3. Error Response
1. 상품 정보를 찾을 수 없을 때
- HTTP Status: `404 Not Found`
- Content-Type: application/json
```json
{
    "errorCode": "ITEM_NOT_FOUND",
    "message": "상품 정보를 찾을 수 없습니다."
}
```


---

## 7. 선착순 쿠폰 발급 API
### 7.1. 시퀀스다이어그램
![7.쿠폰 발급](/docs/sequnceDiagram/7.issueCoupon.png)
보유 쿠폰 -> 선착순 쿠폰 정정필요
### 7.2. API 명세
#### 7.2.1. Request
- Endpoint: `POST /api/coupon/{userId}/issue`
- Content-Type: application/json

```json
{
  "couponId": 1234
}
```
#### 7.2.2. Response
- HTTP Status: `201 Created`
- Content-Type: application/json
```json
{
    "userId": 52,
    "couponId":  1234,
    "couponCode": "2025NEWYEAR",
    "type": "fixed",
    "value": 10000,
    "expirationDate": "2025-12-31",
    "issuedAt": "2025-01-03T12:00:00Z"
}
 ```
#### 7.2.3. Error Response
1. 쿠폰 정보를 찾을 수 없을 때
- HTTP Status: `404 Not Found`
- Content-Type: application/json
```json
{
    "errorCode": "COUPON_NOT_FOUND",
    "message": "쿠폰 정보를 찾을 수 없습니다."
}
```
2. 쿠폰 발급이 마감되었을 때
- HTTP Status: `409 Conflict`
- Content-Type: application/json
```json
{
    "errorCode": "COUPON_NOT_AVAILABLE",
    "message": "마감된 쿠폰으로 발급받으실 수 없습니다."
}
```


---

## 8. 보유 쿠폰 조회 API
### 8.1. 시퀀스다이어그램
![8.보유 쿠폰 조회](/docs/sequnceDiagram/8.getCoupon.png)
### 8.2. API 명세
#### 8.2.1. Request
- Endpoint: `GET /api/coupon/{userId}/`
- Content-Type: application/json

#### 8.2.2. Response
- HTTP Status: `200 Ok`
- Content-Type: application/json
```json
{
  "userId": 21,
  "couponList": [
    {
      "couponId": 123123,
      "couponCode": "2025NEWYEAR",
      "type": "fixed",
      "value": 10000,
      "expirationDate": "2025-12-31",
      "issuedAt": "2025-01-03T12:00:00Z",
      "status": "active"
    },
    {
      "couponId": 65211,
      "couponCode": "THANKYOU",
      "type": "percent",
      "value": 20,
      "expirationDate": "2024-12-31",
      "issuedAt": "2024-10-30T12:00:00Z",
      "status": "expired"
    },
    ///
  ]
}
 ```
#### 8.2.3. Error Response
1. 사용자값이 존재하지 않을 때
- HTTP Status: 404 Not Found
- Content-Type: application/json
```json
{
    "errorCode": "USER_NOT_FOUND",
    "message": "사용자 정보를 찾을 수 없습니다."
}
```
