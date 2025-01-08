package kr.hhplus.be.server.api.user.controller;

import kr.hhplus.be.server.api.user.dto.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    //잔액 조회
    @GetMapping("/{userId}/balance")
    public ResponseEntity<Object> getUserBalance(@PathVariable Long userId) {
        Map<String, Object> response = Map.of(
                "balance", 20000,
                "userId", userId);
        return ResponseEntity.ok(response);
    }

    //잔액 충전
    @PostMapping("/{userId}/balance/charge")
    public ResponseEntity<Object> chargeUserBalance(@PathVariable Long userId, @RequestBody UserRequest userRequest) {
        Map<String, Object> response = Map.of(
                "balance", 15000,
                "message", "잔액이 성공적으로 충전됐습니다.",
                "userId", userId,
                "chargedAmount", 30000
        );
        return ResponseEntity.ok(response);
    }
}