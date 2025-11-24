package kr.kro.moonlightmoist.shopapi.review.controller;

import kr.kro.moonlightmoist.shopapi.review.domain.Review;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewDTO;
import kr.kro.moonlightmoist.shopapi.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{productId}")
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("productId") Long productId){
        List<ReviewDTO> reviews = reviewService.getList(productId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("")
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

}
