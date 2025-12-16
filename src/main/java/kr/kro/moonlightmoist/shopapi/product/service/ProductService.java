package kr.kro.moonlightmoist.shopapi.product.service;

import kr.kro.moonlightmoist.shopapi.product.dto.*;
import kr.kro.moonlightmoist.shopapi.review.dto.PageRequestDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.PageResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    // 상품 등록
    Long register(ProductRequest dto);
    // s3 에 업로드한 이미지 url 추가
    void addImageUrls(Long id, ProductImagesUrlDTO dto);
    // 카테고리별 상품 조회
    PageResponseDTO<ProductResForList> searchProductsByCategory(List<Long> depth3CategoryIds, PageRequestDTO pageRequest);
    // 상품 단일 조회
    ProductResForDetail searchProductById(Long id);
    // 관리자페이지에서 검색 조건으로 상품 리스트 조회
    List<ProductResForList> searchProductsByCondition(ProductSearchCondition condition);
    // 검색조건으로 상품 리스트 조회 페이징처리
    PageResponseDTO<ProductResForList> searchProductsByConditionWithPaging(ProductSearchCondition condition,int page, int size );
    // 상품 수정
    Long modify(Long id, ProductRequest dto);

    // 상품 삭제
}
