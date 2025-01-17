package kr.hhplus.be.server.common.exception;

public enum ErrorCode {
    /*USER*/
    USER_NOT_FOUND("USER/NOT_FOUND", "사용자를 찾을 수 없습니다."),
    EXIST_USER("USER/EXISTS", "이미 존재하는 사용자입니다."),

    /*PRODUCT*/
    PRODUCT_NOT_FOUND("PRODUCT/NOT_FOUND", "상품 정보를 찾을 수 없습니다."),
    NOT_ENOUGH_STOCK("PRODUCT/NOT_ENOUGH_STOCK", "재고가 부족합니다."),

    /*ORDER*/
    ORDER_NOT_FOUND("ORDER/NOT_FOUND", "주문 정보를 찾을 수 없습니다."),
    INVALID_ORDER("ORDER/INVALID", "유효하지 않은 주문 정보입니다."),

    /*COUPON*/
    COUPON_NOT_FOUND("COUPON/NOT_FOUND", "쿠폰 정보를 찾을 수 없습니다."),
    INVALID_COUPON("COUPON/INVALID", "유효하지 않은 쿠폰 정보입니다."),
    EXCEEDED_QUANTITY_COUPON("COUPON/EXCEEDED_QUANTITY_COUPON", "쿠폰 발급 수량이 초과되었습니다."),

    /*PAYMENT*/
    PAYMENT_NOT_FOUND("PAYMENT/NOT_FOUND", "결제 정보를 찾을 수 없습니다."),
    INVALID_PAYMENT("PAYMENT/INVALID", "유효하지 않은 결제 정보입니다."),

    /*WALLET*/
    WALLET_NOT_FOUND("WALLET/NOT_FOUND", "지갑 정보를 찾을 수 없습니다."),


;


    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
