package kr.kro.moonlightmoist.shopapi.order.service;

import com.siot.IamportRestClient.response.Payment;
import kr.kro.moonlightmoist.shopapi.order.domain.Order;
import kr.kro.moonlightmoist.shopapi.order.domain.OrderProduct;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderProductRequestDTO;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderRequestDTO;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    Long createOrder(OrderRequestDTO dto, Long userId);
    OrderResponseDTO getOneOrder(Long orderId);
    List<OrderResponseDTO> getOrderList(Long userId);
    // 결제 금액과 db에 저장된 주문 총 금액이 일치하는지 확인하기 위해 필요한 메서드
    BigDecimal getExpectedAmount(String merchantUid);
    // 결제 검증이 끝나고 주문 상품 상태를 결제 완료로 변경
    void completeOrder(String merchantUid);

}
