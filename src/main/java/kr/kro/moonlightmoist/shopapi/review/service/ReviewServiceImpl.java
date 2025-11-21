package kr.kro.moonlightmoist.shopapi.review.service;

import jakarta.transaction.Transactional;
import kr.kro.moonlightmoist.shopapi.brand.domain.Brand;
import kr.kro.moonlightmoist.shopapi.brand.repository.BrandRepository;
import kr.kro.moonlightmoist.shopapi.category.domain.Category;
import kr.kro.moonlightmoist.shopapi.category.repository.CategoryRepository;
import kr.kro.moonlightmoist.shopapi.product.domain.ExposureStatus;
import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.domain.SaleStatus;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductRepository;
import kr.kro.moonlightmoist.shopapi.review.domain.Review;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewImage;
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
        Category category = Category.builder()
                .name("메이크업1")
                .build();
        categoryRepository.save(category);
        Brand brand = Brand.builder()
                .name("스킨푸드1")
                .build();
        brandRepository.save(brand);
        Product product = Product.builder()
                .productName("상품1")
                .brand(brand)
                .category(category)
                .cancelable(true)
                .exposureStatus(ExposureStatus.EXPOSURE)
                .saleStatus(SaleStatus.ON_SALE)
                .searchKeywords("메이크업1, 스킨푸드1")
                .productCode("code1")
                .build();
        return productRepository.save(product);
    }

    public User getUser() {
        UserGrade userGrade = UserGrade.BRONZE;
        UserRole userRole = UserRole.USER;
        User user = User.builder()
                .loginId("user1234")
                .passwordHash("1234")
                .name("user")
                .phoneNumber("012-3456-7890")
                .email("a@aaa.com")
                .birthDate(LocalDate.now())
                .postalCode("123456")
                .address("그린")
                .addressDetail("컴퓨터")
                .emailAgreement(true)
                .smsAgreement(true)
                .deleted(false)
                .userGrade(userGrade)
                .userRole(userRole)
                .build();
        return userRepository.save(user);
    }

    @Override
    public List<ReviewDTO> getList(Long productId) {
        return List.of();
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

}
