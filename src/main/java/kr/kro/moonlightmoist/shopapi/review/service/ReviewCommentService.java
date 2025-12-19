package kr.kro.moonlightmoist.shopapi.review.service;

import kr.kro.moonlightmoist.shopapi.review.dto.ReviewCommentDTO;

import java.util.List;

public interface ReviewCommentService {
    //리뷰 댓글 전체 조회
    List<ReviewCommentDTO> getList(Long reviewId);
    //리뷰 댓글 등록
    Long register(ReviewCommentDTO dto);
    //리뷰 댓글 수정
    ReviewCommentDTO modify(ReviewCommentDTO dto);
    //리뷰 댓글 삭제
    void remove(Long id);

}
