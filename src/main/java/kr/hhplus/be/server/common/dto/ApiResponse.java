package kr.hhplus.be.server.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private HttpStatus status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponse(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T data, LocalDateTime timestamp) {
        return new ApiResponse<>(httpStatus, message, data);
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, T data, LocalDateTime timestamp) {
        return of(httpStatus, httpStatus.name(), data, timestamp);
    }

    public static <T> ApiResponse<T> ok(T data, LocalDateTime timestamp) {
        return of(HttpStatus.OK, data, timestamp);
    }

    public static <T> ApiResponse<T> error(String message, LocalDateTime timestamp) {
        return of(HttpStatus.BAD_REQUEST, message, null, timestamp);
    }
}