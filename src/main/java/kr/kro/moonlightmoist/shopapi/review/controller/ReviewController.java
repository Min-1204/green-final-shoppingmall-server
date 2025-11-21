package kr.kro.moonlightmoist.shopapi.review.controller;

import kr.kro.moonlightmoist.shopapi.review.dto.ReviewDTO;
import kr.kro.moonlightmoist.shopapi.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

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
