package kr.kro.moonlightmoist.shopapi.review.controller;

import kr.kro.moonlightmoist.shopapi.aws.service.S3UploadService;
import kr.kro.moonlightmoist.shopapi.pagination.PageRequestDTO;
import kr.kro.moonlightmoist.shopapi.pagination.PageResponseDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewImageUrlDTO;
import kr.kro.moonlightmoist.shopapi.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/review")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {

    private final ReviewService reviewService;
    private final S3UploadService s3UploadService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<PageResponseDTO<ReviewDTO>> getList(
            @PathVariable("productId") Long productId,
            @RequestParam(defaultValue = "latest") String sort, //기본 최신순
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
            ) {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
            .page(page)
            .size(size)
            .build();

        PageResponseDTO<ReviewDTO> reviews = reviewService.getList(productId, sort, pageRequestDTO);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PageResponseDTO<ReviewDTO>> getMyReviews(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
          ) {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
            .page(page)
            .size(size)
            .build();

        PageResponseDTO<ReviewDTO> reviews = reviewService.getListByUser(userId, pageRequestDTO);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/add")
    public ResponseEntity<String> register(
            @RequestPart("review") ReviewDTO dto,
            @RequestPart(value = "reviewImage", required = false) List<MultipartFile> reviewImage
    ){
        Long id = reviewService.register(dto);

        if (reviewImage != null && !reviewImage.isEmpty()) {
            ReviewImageUrlDTO reviewImageUrlDTO = ReviewImageUrlDTO.builder()
                    .imageUrls(new ArrayList<>())
                    .build();

            for(int i=0; i<reviewImage.size(); i++){
                String url = s3UploadService.uploadOneFile(reviewImage.get(i), "reviews/" + id + "/");
                reviewImageUrlDTO.getImageUrls().add(url);
            }

            reviewService.addImageUrls(id, reviewImageUrlDTO);
        }

        return ResponseEntity.ok("성공");
    }

    @PutMapping("/modify/{reviewId}")
    public ResponseEntity<String> modify(
            @PathVariable("reviewId") Long reviewId,
            @RequestPart("review") ReviewDTO dto,
            @RequestPart(value = "reviewImage", required = false) List<MultipartFile> reviewImage
    ){
        dto.setId(reviewId);
        reviewService.modify(dto);

        if (reviewImage != null && !reviewImage.isEmpty()) {
            ReviewImageUrlDTO reviewImageUrlDTO = ReviewImageUrlDTO.builder()
                    .imageUrls(new ArrayList<>())
                    .build();

            for(int i=0; i<reviewImage.size(); i++){
                String url = s3UploadService.uploadOneFile(reviewImage.get(i),  "reviews/" + reviewId + "/");
                reviewImageUrlDTO.getImageUrls().add(url);
            }

            reviewService.addImageUrls(reviewId, reviewImageUrlDTO);
        }

        return ResponseEntity.ok("성공");
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> remove(@PathVariable("reviewId") Long reviewId){
        reviewService.remove(reviewId);
        return ResponseEntity.ok("성공");
    }

    @GetMapping("/product/{productId}/avg")
    public ResponseEntity<Double> getAvgRating(@PathVariable Long productId){
        double avgRating = reviewService.getAvgRating(productId);
        return ResponseEntity.ok(avgRating);
    }

    @GetMapping("/product/{productId}/count")
    public ResponseEntity<Integer> getReviewCount(@PathVariable Long productId){
        int reviewCount = reviewService.getReviewTotalCount(productId);
        return ResponseEntity.ok(reviewCount);
    }

    @GetMapping("/product/{productId}/{rating}/count")
    public ResponseEntity<Integer> getRatingByCount(
            @PathVariable Long productId,
            @PathVariable Integer rating
    ){
        int ratingBycount = reviewService.getRatingByCount(productId, rating);
        return ResponseEntity.ok(ratingBycount);
    }

    @GetMapping("/product/{productId}/positive")
    public ResponseEntity<Integer> getPositiveReview(@PathVariable Long productId){
        int positiveReview = reviewService.getPositiveReview(productId);
        return ResponseEntity.ok(positiveReview);
    }

}
