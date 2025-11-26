package kr.kro.moonlightmoist.shopapi.review.service;

import jakarta.transaction.Transactional;
import kr.kro.moonlightmoist.shopapi.brand.repository.BrandRepository;
import kr.kro.moonlightmoist.shopapi.category.repository.CategoryRepository;
import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductRepository;
import kr.kro.moonlightmoist.shopapi.review.domain.Review;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewDTO;
import kr.kro.moonlightmoist.shopapi.review.repository.ReviewRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.domain.UserGrade;
import kr.kro.moonlightmoist.shopapi.user.domain.UserRole;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final UserRepository userRepository;

    public Product getProduct() {
        return productRepository.findById(1L).get();
    }

    public User getUser() {
        return userRepository.findById(2L).get();
    }

    @Override
    public List<ReviewDTO> getList(Long productId, String sort) {
        List<Review> reviews = reviewRepository.findByProductId(productId);

        if (sort.equals("latest")) {
            reviews.sort((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt()));
        } else if (sort.equals("oldest")) {
            reviews.sort((r1, r2) -> r1.getCreatedAt().compareTo(r2.getCreatedAt()));
        } else if (sort.equals("ratingDesc")) {
            reviews.sort((r1, r2) -> {
                int ratingDesc = r2.getRating() - r1.getRating();
                if (ratingDesc != 0) return ratingDesc;
                return r2.getCreatedAt().compareTo(r1.getCreatedAt());
            }); //내림차순
        } else if (sort.equals("ratingAsc")) {
            reviews.sort((r1, r2) -> {
                int ratingAsc = r1.getRating() - r2.getRating();
                if (ratingAsc != 0) return ratingAsc;
                return r1.getCreatedAt().compareTo(r2.getCreatedAt());
            }); //오름차순
        }

        List<ReviewDTO> reviewDTO = reviews.stream().map(review -> {
            return ReviewDTO.builder()
                    .id(review.getId())
                    .productId(review.getProduct().getId())
                    .userId(review.getUser().getId())
                    .loginId(review.getUser().getLoginId())
                    .content(review.getContent())
                    .rating(review.getRating())
                    .createdAt(review.getCreatedAt())
                    .build();
        }).toList();

        return reviewDTO;
    }

    @Override
    public List<ReviewDTO> getListByUser(Long userId) {
        List<Review> list = reviewRepository.findByUserId(userId);
        return list.stream()
                .map(review -> ReviewDTO.builder()
                        .id(review.getId())
                        .content(review.getContent())
                        .rating(review.getRating())
                        .userId(review.getUser().getId())
                        .productId(review.getProduct().getId())
                        .createdAt(review.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public Long register(ReviewDTO dto) {

        Product product = getProduct();
        User user = getUser();

        Review review = Review.builder()
                .user(user)
                .content(dto.getContent())
                .rating(dto.getRating())
                .product(product)
                .build();

        Review reviewSave = reviewRepository.save(review);
        return reviewSave.getId();

    }

    @Override
    public ReviewDTO modify(ReviewDTO reviewDTO) {
        Optional<Review> foundReview = reviewRepository.findById(reviewDTO.getId());
        Review review = foundReview.orElseThrow();

        review.changeContent(reviewDTO.getContent());
        review.changeRating(reviewDTO.getRating());

        return ReviewDTO.builder()
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }

    @Override
    public void remove(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

}
