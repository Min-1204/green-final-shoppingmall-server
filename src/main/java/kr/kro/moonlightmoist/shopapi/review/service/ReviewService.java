package kr.kro.moonlightmoist.shopapi.review.service;

import kr.kro.moonlightmoist.shopapi.review.dto.ReviewPageRequestDTO; // ⭐️ 추가
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewPageResponseDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewImageUrlDTO;


public interface ReviewService {
    //상품의 리뷰 전체 조회
    ReviewPageResponseDTO<ReviewDTO> getList(Long productId, String sort, ReviewPageRequestDTO reviewPageRequestDTO);
    //나의 리뷰 전체 조회(로그인한 유저 본인 리뷰)
    ReviewPageResponseDTO<ReviewDTO> getListByUser(Long userId, ReviewPageRequestDTO reviewPageRequestDTO);
    //리뷰 등록
    Long register(ReviewDTO dto);
    //리뷰 수정
    ReviewDTO modify(ReviewDTO reviewDTO);
    //리뷰 삭제
    void remove(Long id, Long userId);
    //s3 에 업로드한 이미지 url 추가
    void addImageUrls(Long id, ReviewImageUrlDTO dto);
    //별점 평균
    Double getAvgRating(Long productId);
    //리뷰 총 개수
    int getReviewTotalCount(Long productId);
    //별점별(5,4,3,2,1) 개수
    int getRatingByCount(Long productId, Integer rating);
    //긍정적 별점 개수(5,4)
    int getPositiveReview(Long productId);
}
