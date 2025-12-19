package kr.kro.moonlightmoist.shopapi.review.service;

public interface ReviewLikeService {

    boolean toggleReviewLike(Long reviewId);

    int countReviewLike(Long reviewId);
}
