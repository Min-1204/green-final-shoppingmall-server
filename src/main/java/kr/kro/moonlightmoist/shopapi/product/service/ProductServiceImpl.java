package kr.kro.moonlightmoist.shopapi.product.service;

import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductRequest;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    Product toEntity (ProductRequest dto) {
        return Product.builder()
                .productName(dto.getProductName())
                .searchKeywords(dto.getSearchKeywords())
                .exposureStatus(dto.getExposureStatus())
                .saleStatus(dto.getSaleStatus())
                .description(dto.getDescription())
                .cancelable(dto.isCancelable())
                .deleted(dto.isDeleted())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public Long register(ProductRequest dto) {

        Product entity = toEntity(dto);

        Product savedProduct = productRepository.save(entity);

        System.out.println("savedProduct = " + savedProduct);

        return savedProduct.getId();
    }
}
