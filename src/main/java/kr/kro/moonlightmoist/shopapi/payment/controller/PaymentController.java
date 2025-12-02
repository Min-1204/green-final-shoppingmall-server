package kr.kro.moonlightmoist.shopapi.payment.controller;

import kr.kro.moonlightmoist.shopapi.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/payments")
@CrossOrigin(origins = "*", allowedHeaders = "*",
            methods = {
                RequestMethod.GET,
                    RequestMethod.DELETE,
                    RequestMethod.POST,
                    RequestMethod.OPTIONS,
                    RequestMethod.PUT
            })
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(@RequestBody Map<String, String> requestData) {
        String impUid = requestData.get("imp_uid");
        String merchantUid = requestData.get("merchant_uid");

        try {
            // Service의 검증 로직 호출
            paymentService.verifyPaymentAndCompleteOrder(impUid, merchantUid);
            return ResponseEntity.ok("결제 검증 및 주문 처리가 완료되었습니다.");
        } catch (Exception e) {
            // 검증 실패 시 400 에러 반환
            return ResponseEntity.status(400).body("결제 검증 실패: " + e.getMessage());
        }
    }
}
