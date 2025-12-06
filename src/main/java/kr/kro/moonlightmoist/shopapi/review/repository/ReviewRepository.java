package kr.kro.moonlightmoist.shopapi.review.repository;

import kr.kro.moonlightmoist.shopapi.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByUserId(Long userId, Pageable pageable);

    Page<Review> findByProductId(Long productId, Pageable pageable);

    //좋아요 개수 기준 정렬 및 페이징 메서드
    @Query(
        value = """
              SELECT r
              FROM Review r
              WHERE r.product.id = :productId
              ORDER BY (
                  SELECT COUNT(rl.id)
                  FROM ReviewLike rl
                  WHERE rl.review = r AND rl.deleted = FALSE
              ) DESC, r.createdAt DESC
          """,
        countQuery = """
              SELECT COUNT(r)
              FROM Review r
              WHERE r.product.id = :productId
          """
    )
    Page<Review> findByProductIdLike(@Param("productId") Long productId, Pageable pageable);

    @Query("select avg(r.rating) from Review r where r.product.id = :productId")
    public Double reviewAvgRating(@Param("productId") Long productId);

    @Query("select count(r.id) from Review r where r.product.id = :productId")
    public int reviewTotalCount(@Param("productId") Long productId);

    @Query("select count(r) from Review r where r.product.id = :productId and r.rating = :rating")
    public int ratingByCount(@Param("productId") Long productId, @Param("rating") int rating);

    @Query("select count(r) from Review r where r.product.id = :productId and r.rating in (4,5)")
    public int positiveReview(@Param("productId") Long productId);
}
