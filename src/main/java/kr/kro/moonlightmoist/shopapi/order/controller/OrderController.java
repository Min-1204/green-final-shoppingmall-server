package kr.kro.moonlightmoist.shopapi.order.controller;

import kr.kro.moonlightmoist.shopapi.order.dto.OrderRequestDTO;
import kr.kro.moonlightmoist.shopapi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/register")
    public ResponseEntity<String> registerOrder(@RequestBody OrderRequestDTO orderRequestDTO, Long userId) {
        log.info("registerOrder 메서드 실행 dto :{}", orderRequestDTO);
        return ResponseEntity.ok(orderService.register(orderRequestDTO, userId));
    }
}
