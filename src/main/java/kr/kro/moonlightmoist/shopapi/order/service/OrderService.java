package kr.kro.moonlightmoist.shopapi.order.service;

import kr.kro.moonlightmoist.shopapi.order.dto.*;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Long createOrder(OrderRequestDTO dto, Long userId);
    OrderResponseDTO getOneOrder(Long orderId);
    List<OrderResponseDTO> getOrderList(Long userId);
    // 결제 금액과 db에 저장된 주문 총 금액이 일치하는지 확인하기 위해 필요한 메서드
    BigDecimal getExpectedAmount(String merchantUid);
    // 결제 검증이 끝나고 주문 상품 상태를 결제 완료로 변경
    void completeOrder(String merchantUid);
    void deleteOneOrder(Long orderId);
    List<OrderResBySearch> searchOrdersByCondition(OrderSearchCondition condition);
    void comfirmOrder(Long orderId);

}
