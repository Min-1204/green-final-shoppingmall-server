package kr.kro.moonlightmoist.shopapi.coupon.repository;

import kr.kro.moonlightmoist.shopapi.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponCustomRepository {
    Optional<Coupon> findByCouponCode(String couponCode);
}
