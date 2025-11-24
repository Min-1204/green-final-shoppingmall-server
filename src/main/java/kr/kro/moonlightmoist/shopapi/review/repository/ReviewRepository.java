package kr.kro.moonlightmoist.shopapi.review.repository;

import kr.kro.moonlightmoist.shopapi.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProductId(Long productId);
}
