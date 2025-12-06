package kr.kro.moonlightmoist.shopapi.review.service;

import jakarta.transaction.Transactional;
import kr.kro.moonlightmoist.shopapi.review.domain.Review;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewComment;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewCommentDTO;
import kr.kro.moonlightmoist.shopapi.review.repository.ReviewCommentRepository;
import kr.kro.moonlightmoist.shopapi.review.repository.ReviewRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReviewCommentServiceImpl implements ReviewCommentService {

    private final ReviewRepository reviewRepository;
    private final ReviewCommentRepository reviewCommentRepository;
    private final UserRepository userRepository;

    public Review getReview(Long reviewId) {
    return reviewRepository.findById(reviewId).get();
  }

    public User getUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public List<ReviewCommentDTO> getList(Long reviewId) {
        List<ReviewComment> reviewComments = reviewCommentRepository.findByReviewId(reviewId);

        List<ReviewCommentDTO> reviewCommentDTO = reviewComments.stream().map(reviewComment -> {
            return ReviewCommentDTO.builder()
                    .id(reviewComment.getId())
                    .reviewId(reviewComment.getReview().getId())
                    .userId(reviewComment.getUser().getId())
                    .loginId(reviewComment.getUser().getLoginId())
                    .content(reviewComment.getContent())
                    .createAt(reviewComment.getCreatedAt())
                    .build();
        }).toList();
        return reviewCommentDTO;
  }

    @Override
    public Long register(@RequestBody ReviewCommentDTO dto) {
        Review review = getReview(dto.getReviewId());
        User user = getUser(dto.getUserId());

        ReviewComment reviewComment = ReviewComment.builder()
                .content(dto.getContent())
                .review(review)
                .user(user)
                .build();

        ReviewComment saveReviewComment = reviewCommentRepository.save(reviewComment);

        return saveReviewComment.getId();
  }

    @Override
    public ReviewCommentDTO modify(ReviewCommentDTO dto) {
        Optional<ReviewComment> reviewCommentId = reviewCommentRepository.findById(dto.getId());
        ReviewComment reviewComment = reviewCommentId.get();

        //본인 댓글만 수정
        if (!reviewComment.getUser().getId().equals(dto.getUserId())) {
            throw new RuntimeException("본인의 댓글만 수정할 수 있습니다.");
        }

        reviewComment.changeContent(dto.getContent());

        return ReviewCommentDTO.builder()
                .content(dto.getContent())
                .build();
  }

    @Override
    public void remove(Long id, Long userId) {
        Optional<ReviewComment> reviewCommentId = reviewCommentRepository.findById(id);
        ReviewComment reviewComment = reviewCommentId.get();

        //본인 댓글만 삭제
        if (!reviewComment.getUser().getId().equals(userId)) {
            throw new RuntimeException("본인의 댓글만 삭제할 수 있습니다.");
        }

        reviewCommentRepository.deleteById(id);
    }
}
