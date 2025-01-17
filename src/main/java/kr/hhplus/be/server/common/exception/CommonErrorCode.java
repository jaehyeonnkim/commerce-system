package kr.hhplus.be.server.common.exception;

public enum CommonErrorCode {
    GENERAL_ERROR("GENERAL_ERROR", "알 수 없는 오류가 발생했습니다."),
    UNAUTHORIZED("COMMON_UNAUTHORIZED", "인증되지 않은 요청입니다.");


    private final String code;
    private final String message;

    CommonErrorCode(String code, String message) {
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