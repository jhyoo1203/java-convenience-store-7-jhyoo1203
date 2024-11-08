# 편의점

구매자의 할인 혜택과 재고 상황을 고려하여 최종 결제 금액을 계산하고 안내하는 결제 시스템을 구현한다.

## 🔥 구현할 기능 목록

---

### 입력
- [ ] 구매할 상품과 수량을 입력 받는다.
    - 상품명, 수량은 하이픈(-)으로, 개별 상품은 대괄호([])로 묶어 쉼표(,)로 구분한다.
    ```
    구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
    [콜라-10],[사이다-3]
    ```
- [ ] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 그 수량만큼 추가 여부를 입력받는다.
    - Y: 증정 받을 수 있는 상품을 추가한다.
    - N: 증정 받을 수 있는 상품을 추가하지 않는다.
    ```
    현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)
    Y
    ```

- [ ] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부를 입력받는다.
    - Y: 일부 수량에 대해 정가로 결제한다.
    - N: 정가로 결제해야하는 수량만큼 제외한 후 결제를 진행한다.
    ```
    현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)
    Y
    ```

- [ ] 멤버십 할인 적용 여부를 입력 받는다.
    - Y: 멤버십 할인을 적용한다.
    - N: 멤버십 할인을 적용하지 않는다.
    ```
    멤버십 할인을 받으시겠습니까? (Y/N)
    Y
    ```

- [ ] 추가 구매 여부를 입력 받는다.
    - Y: **재고가 업데이트된 상품 목록을 확인** 후 추가로 구매를 진행한다.
    - N: 구매를 종료한다.
    ```
    감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
    Y
    ```

### 출력
- [ ] 환영 인사와 함께 상품명, 가격, 프로모션 이름, 재고를 안내한다.
    - [ ] 만약 재고가 0개라면 `재고 없음`을 출력한다.
    ```
    안녕하세요. W편의점입니다.
    현재 보유하고 있는 상품입니다.
    
    콜라 1,000원 10개 탄산2+1
    콜라 1,000원 10개
    사이다 1,000원 8개 탄산2+1
    사이다 1,000원 7개
    오렌지주스 1,800원 9개 MD추천상품
    ```
  
- [ ] 구매 상품 내역, 증정 상품 내역, 금액 정보를 출력한다.
    ```
    ===========W 편의점=============
    상품명		수량	금액
    콜라		3 	3,000
    에너지바 		5 	10,000
    ===========증	정=============
    콜라		1
    ==============================
    총구매액		8	13,000
    행사할인			-1,000
    멤버십할인			-3,000
    내실돈			 9,000
    ```
---

### 파일 불러오기
- 구현에 필요한 상품 목록과 행사 목록을 파일 입출력을 통해 불러온다.
  - `src/main/resources/products.md`과 `src/main/resources/promotions.md` 파일을 이용한다.
  - 두 파일 모두 내용의 형식을 유지한다면 값은 수정할 수 있다.

---
### 상품
- [ ] 상품에는 상품명, 상품 가격, 재고 수량이 있다.
- [ ] 재고를 차감할 수 있다.
- [ ] 구매 가능 여부를 판단할 수 있다.

### 프로모션 상품
- [ ] 프로모션 상품 도메인은 상품 속성 클래스를 상속한다.
- [ ] 프로모션 상품 도메인은 프로모션명을 가지고 있다.
  - ex) `MD추천상품`, `탄산2+1`, `반짝할인`
- [ ] 프로모션 상품 도메인은 증정 유형을 가지고 있다.
- [ ] 프로모션 재고를 차감할 수 있다.
- [ ] 프로모션 적용 가능 여부를 판단할 수 있다.

### 상품 리스트
- [ ] 상품 리스트 도메인은 모든 상품들을 저장한다.
- [ ] 상품명과 갯수로 상품을 구매한다.

### 프로모션
- [ ] 프로모션에는 이름, 구매 갯수, 증정 갯수, 시작일, 종료일이 있다.
- [ ] 현재 시간이 행사 기간인지 판단할 수 있다.

### 프로모션 리스트
- [ ] 이름을 통해 프로모션을 반환한다.

### 멤버십 할인
- [ ] 멤버십 할인 도메인은 할인률을 가지고 있다.
- [ ] 멤버십 할인 도메인은 최대 한도를 가지고 있다.
- [ ] 할인 가능 여부를 판단할 수 있다.

### 구매 상품 내역
- [ ] 구매 상품 내역은 record로 정의한다.
- [ ] 구매 상품 내역엔 구매한 상품명, 수량, 가격이 포함된다.

### 증정 상품 내역
- [ ] 증정 상품 내역은 record로 정의한다.
- [ ] 증성 상품 내역엔 프로모션 상품명, 수량이 포함된다.

### 금액 정보
- [ ] 금액 정보는 record로 정의한다.
- [ ] 금액 정보엔 총구매액, 행사할인, 멤버십할인, 내실돈이 포함된다.
  - 총구매액: 구매한 상품의 총 수량과 총 금액
  - 행사할인: 프로모션에 의해 할인된 금액
  - 멤버십할인: 멤버십에 의해 추가로 할인된 금액
  - 내실돈: 최종 결제 금액

---
### 🚫 예외 처리
- 사용자가 잘못된 값을 입력했을 때, `[ERROR]`로 시작하는 오류 메시지와 함께 상황에 맞는 안내를 출력한다.
    - 구매할 상품과 수량 형식이 올바르지 않은 경우: `[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.`
    - 존재하지 않는 상품을 입력한 경우: `[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.`
    - 구매 수량이 재고 수량을 초과한 경우: `[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.`
    - 기타 잘못된 입력의 경우: `[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.`




---
### 라이브러리
- `camp.nextstep.edu.missionutils`에서 제공하는 DateTimes 및 Console API를 사용하여 구현해야 한다.
    - 현재 날짜와 시간을 가져오려면 `camp.nextstep.edu.missionutils.DateTimes`의 `now()`를 활용한다.
    - 사용자가 입력하는 값은 `camp.nextstep.edu.missionutils.Console`의 `readLine()`을 활용한다.
