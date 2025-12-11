package kr.kro.moonlightmoist.shopapi.product.repository;

import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    Optional<ProductOption> findByOptionName(String name);

    @Query("SELECT o FROM ProductOption o WHERE o.id IN :ids")
    List<ProductOption> findByIds(List<Long> ids);
}
