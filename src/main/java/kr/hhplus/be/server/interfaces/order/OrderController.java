package kr.hhplus.be.server.interfaces.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    //상품 주문
    @PostMapping("/{userId}")
    public ResponseEntity<Object> orderProducts(@PathVariable Long userId,
                                                @RequestBody OrderRequest orderRequest) {

        Map<String, Object> response = Map.of(
                "orderId", 2149,
                "date","2025-01-02T08:20:00",
                "totalAmount" , 58000,
                "message", "주문이 성공적으로 완료되었습니다."
        );

        return ResponseEntity.ok(response);
    }
}
