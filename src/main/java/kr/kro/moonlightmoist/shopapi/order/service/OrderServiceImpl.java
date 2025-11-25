package kr.kro.moonlightmoist.shopapi.order.service;

import kr.kro.moonlightmoist.shopapi.order.domain.Order;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderProductRequestDTO;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderRequestDTO;
import kr.kro.moonlightmoist.shopapi.order.repository.OrderRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public String createOrderNumber() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        //랜덤 6자리 생성(UUID 사용)
        String random = UUID.randomUUID()
                .toString()
                .substring(0,6)
                .toUpperCase();
        return date + "-" + random;
    }

    public int calcTotalProductAmount(List<OrderProductRequestDTO> orderProducts) {
        int totalProductAmount = 0;
        for(OrderProductRequestDTO o : orderProducts){
            totalProductAmount += o.getPurchasedPrice() * o.getQuantity();
        }
        return totalProductAmount;
    }

    public int calcCouponDiscountAmount(int totalProductAmount, Long couponId) {
        if(couponId == null){
            return 0;
        }
        else return 3000;
    }

    public Order createOrder(OrderRequestDTO dto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        String orderNumber = createOrderNumber();
        LocalDate expectedDeliveryDate = LocalDate.now().plusDays(2);
        int totalProductAmount = calcTotalProductAmount(dto.getOrderProducts());
        int discountAmount = calcCouponDiscountAmount(totalProductAmount,dto.getCouponId());
        int finalAmount = totalProductAmount- discountAmount - dto.getUsedPoints();

        Order order = Order.builder()
                .user(user)
                .orderNumber(orderNumber)
                .paymentMethod(dto.getPaymentMethod())
                .deliveryFee(dto.getDeliveryFee())
                .expectedDeliveryDate(expectedDeliveryDate)
                .totalProductAmount(totalProductAmount)
                .discountAmount(discountAmount)
                .usedpoints(dto.getUsedPoints())
                .finalAmount(finalAmount)
                .receiverName(dto.getReceiverName())
                .receiverPhone(dto.getReceiverPhone())
                .postalCode(dto.getPostalCode())
                .streetAddress(dto.getStreetAddress())
                .detailedAddress(dto.getDetailedAddress())
                .deliveryRequest(dto.getDeliveryRequest())
                .deleted(false)
                .build();

        return order;
    }

    @Override
    public String register(OrderRequestDTO dto, Long userId) {
        Order order = createOrder(dto, userId);
        orderRepository.save(order);
        return "주문 정보 저장 완료";
    }
}
