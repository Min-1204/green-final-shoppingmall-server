package kr.kro.moonlightmoist.shopapi.review.service;

import kr.kro.moonlightmoist.shopapi.review.dto.ReviewDTO;

import java.util.List;


public interface ReviewService {

    List<ReviewDTO> getList(Long productId);//임시 리뷰 목록
    List<ReviewDTO> getListByUser(Long userId);
    Long register(ReviewDTO dto);
    ReviewDTO modify(ReviewDTO reviewDTO);
    void remove(Long reviewId);
}
