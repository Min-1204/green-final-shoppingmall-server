package kr.kro.moonlightmoist.shopapi.order.service;

public interface OrderCouponService {
    int calcAndUseCoupon(int totalProductAmount, Long userCouponId);
    Long saveCoupon(Long orderId, Long userCouponId, int discountAmount);
    void deleteOrderCoupon(Long orderCouponId);

}
