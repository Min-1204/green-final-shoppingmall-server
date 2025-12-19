package kr.kro.moonlightmoist.shopapi.review.service;

import jakarta.transaction.Transactional;
import kr.kro.moonlightmoist.shopapi.review.domain.Review;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewComment;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewCommentDTO;
import kr.kro.moonlightmoist.shopapi.review.repository.ReviewCommentRepository;
import kr.kro.moonlightmoist.shopapi.review.repository.ReviewRepository;
import kr.kro.moonlightmoist.shopapi.security.CustomUserDetails;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Long register(ReviewCommentDTO dto) {

        //SecurityContext에서 로그인 사용자 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            throw new RuntimeException("로그인 정보가 올바르지 않습니다.");
        }
        String loginId = ((CustomUserDetails) principal).getUsername();

        //loginId로 User 엔티티 조회
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));

        Review review = getReview(dto.getReviewId());

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
        ReviewComment reviewComment = reviewCommentRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        //SecurityContext에서 로그인 사용자 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            throw new RuntimeException("로그인 정보가 올바르지 않습니다.");
        }
        String loginId = ((CustomUserDetails) principal).getUsername();

        //loginId로 User 엔티티 조회
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));

        //본인 댓글만 수정
        if (!reviewComment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("본인의 댓글만 수정할 수 있습니다.");
        }

        reviewComment.changeContent(dto.getContent());

        return ReviewCommentDTO.builder()
                .content(dto.getContent())
                .build();
  }

    @Override
    public void remove(Long id) {
        ReviewComment reviewComment = reviewCommentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        //SecurityContext에서 로그인 사용자 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            throw new RuntimeException("로그인 정보가 올바르지 않습니다.");
        }
        String loginId = ((CustomUserDetails) principal).getUsername();

        //loginId로 User 엔티티 조회
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));

        //본인 댓글만 삭제
        if (!reviewComment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("본인의 댓글만 삭제할 수 있습니다.");
        }

        reviewCommentRepository.deleteById(id);
    }
}
