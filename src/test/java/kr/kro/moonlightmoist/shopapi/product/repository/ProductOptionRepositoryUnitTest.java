package kr.kro.moonlightmoist.shopapi.product.repository;

import kr.kro.moonlightmoist.shopapi.brand.domain.Brand;
import kr.kro.moonlightmoist.shopapi.brand.repository.BrandRepository;
import kr.kro.moonlightmoist.shopapi.category.domain.Category;
import kr.kro.moonlightmoist.shopapi.category.repository.CategoryRepository;
import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import kr.kro.moonlightmoist.shopapi.util.EntityFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@EnableJpaAuditing
class ProductOptionRepositoryUnitTest {

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    private Brand brand;
    private Category category;
    private Product product;

    @BeforeEach
    public void init() {
        brand = brandRepository.save(EntityFactory.createBrand("헤라"));
        category = categoryRepository.save(EntityFactory.createCategory("메이크업", 0, 0));
        product = productRepository.save(EntityFactory.createProduct(category, brand));
    }

    @Test
    @DisplayName("상품옵션 등록 테스트")
    public void saveProductOption_Success() {
        ProductOption savedOption = productOptionRepository.save(createProductOption("상품 옵션이름", product));

        assertThat(savedOption.getId()).isNotNull();
        assertThat(savedOption.getProduct()).isNotNull();
        assertThat(savedOption.getOptionName()).isEqualTo("상품 옵션이름");
        assertThat(savedOption.getPurchasePrice()).isEqualTo(1000);
        assertThat(savedOption.getSellingPrice()).isEqualTo(5000);
        assertThat(savedOption.getCurrentStock()).isEqualTo(30);
        assertThat(savedOption.getInitialStock()).isEqualTo(50);
        assertThat(savedOption.getSafetyStock()).isEqualTo(10);
        assertThat(savedOption.getImageUrl()).isEqualTo("url");
        assertThat(savedOption.getDisplayOrder()).isEqualTo(1);
        assertThat(savedOption.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("상품옵션 이름으로 조회 테스트")
    public void findByOptionName_Success() {
        ProductOption savedOption = productOptionRepository.save(createProductOption("상품 옵션이름",product));
        Optional<ProductOption> foundOption = productOptionRepository.findByOptionName("상품 옵션이름");

        assertThat(foundOption).isPresent();
        assertThat(foundOption.get().getId()).isEqualTo(savedOption.getId());
        assertThat(foundOption.get().getOptionName()).isEqualTo("상품 옵션이름");
    }

    @Test
    @DisplayName("존재하지 않는 상품이름으로 조회시 빈 Optional 반환")
    public void findByOptionName_NotFound() {
        ProductOption savedOption = productOptionRepository.save(createProductOption("상품 옵션이름",product));
        Optional<ProductOption> foundOption = productOptionRepository.findByOptionName("상품 옵션이름2");

        assertThat(foundOption).isEmpty();
    }

    @Test
    @DisplayName("상품옵션 등록 시 상품과의 연관관계 검증")
    public void saveProductOption_WithProductRelation_Success() {
        ProductOption savedOption = productOptionRepository.save(createProductOption("옵션", product));

        assertThat(savedOption.getProduct().getId()).isEqualTo(product.getId());
//        assertThat(product.getProductOptions()).hasSize(1);
    }

    private ProductOption createProductOption(String name, Product product) {
        return ProductOption.builder()
                .product(product)
                .optionName(name)
                .purchasePrice(1000)
                .sellingPrice(5000)
                .currentStock(30)
                .initialStock(50)
                .safetyStock(10)
                .imageUrl("url")
                .displayOrder(1)
                .build();
    }

}
