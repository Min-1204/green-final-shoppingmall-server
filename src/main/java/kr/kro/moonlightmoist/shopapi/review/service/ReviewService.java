package kr.kro.moonlightmoist.shopapi.review.service;

import kr.kro.moonlightmoist.shopapi.review.dto.ReviewDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewImageUrlDTO;

import java.util.List;


public interface ReviewService {
    //상품의 리뷰 전체 조회
    List<ReviewDTO> getList(Long productId, String sort);
    //나의 리뷰 전체 조회(로그인한 유저 본인 리뷰)
    List<ReviewDTO> getListByUser(Long userId);
    //리뷰 등록
    Long register(ReviewDTO dto);
    //리뷰 수정
    ReviewDTO modify(ReviewDTO reviewDTO);
    //리뷰 삭제
    void remove(Long reviewId);
    //s3 에 업로드한 이미지 url 추가
    void addImageUrls(Long id, ReviewImageUrlDTO dto);
}
