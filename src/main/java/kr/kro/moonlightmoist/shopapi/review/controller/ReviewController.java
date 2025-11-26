package kr.kro.moonlightmoist.shopapi.review.controller;

import kr.kro.moonlightmoist.shopapi.review.domain.Review;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewDTO;
import kr.kro.moonlightmoist.shopapi.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/review")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getList(
            @PathVariable("productId") Long productId,
            @RequestParam(defaultValue = "sort") String sort
            ){
        List<ReviewDTO> reviews = reviewService.getList(productId, sort);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDTO>> getMyReviews(@PathVariable Long userId){
        List<ReviewDTO> reviews = reviewService.getListByUser(userId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/add")
    public ResponseEntity<ReviewDTO> register(@RequestBody ReviewDTO dto){
        Long id = reviewService.register(dto);
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .id(id)
                .content(dto.getContent())
                .rating(dto.getRating())
                .files(dto.getFiles())
                .uploadFileNames(dto.getUploadFileNames())
                .build();
        return ResponseEntity.ok(reviewDTO);
    }

    @PutMapping("/modify/{reviewId}")
    public ResponseEntity<ReviewDTO> modify(@PathVariable("reviewId") Long reviewId, @RequestBody ReviewDTO dto){
        ReviewDTO reviewUpdated = ReviewDTO.builder()
                .id(reviewId)
                .content(dto.getContent())
                .rating(dto.getRating())
                .build();
        ReviewDTO modifyReview = reviewService.modify(reviewUpdated);
        return ResponseEntity.ok(modifyReview);
    }

    @DeleteMapping("/delete/{reviewId}")
    public Map<String,String> remove(@PathVariable("reviewId") Long reviewId){
        reviewService.remove(reviewId);
        return Map.of("RESULT","SUCCESS");
    }

}
