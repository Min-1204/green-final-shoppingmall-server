package kr.kro.moonlightmoist.shopapi.review.service;

import kr.kro.moonlightmoist.shopapi.review.dto.ReviewDTO;

import java.util.List;


public interface ReviewService {

    List<ReviewDTO> getList(Long productId);
    Long register(ReviewDTO dto);
    ReviewDTO modify(ReviewDTO reviewDTO);
    void remove(Long reviewId);
}
