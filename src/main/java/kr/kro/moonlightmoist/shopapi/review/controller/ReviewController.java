package kr.kro.moonlightmoist.shopapi.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kro.moonlightmoist.shopapi.aws.service.S3UploadService;
import kr.kro.moonlightmoist.shopapi.review.dto.PageRequestDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.PageResponseDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.ReviewImageUrlDTO;
import kr.kro.moonlightmoist.shopapi.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


//스웨거 @Operation : 해당 API의 설명을 정의
//         summary : API 목록에 보이는 한 줄 요약
//     description : API 상세 설명

//스웨거 @Parameter : API 호출 시 서버로 전달되는 데이터(매개변수)를 정의하고 문서화하는 요소
//         example : “이 파라미터에 이런 값이 들어온다”는 것을 문서상으로 보여주는 샘플 값

//스웨거 @ApiResponse : 하나의 Operation이 반환할 수 있는 응답(Response) 정의,
//                     성공 / 실패 / 예외 케이스별 응답 명세를 Swagger 문서에 명확히 표시하는 용도
//      responseCode : HTTP 상태 코드(문자열)
//       description : 응답 설명


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/review")
@Tag(
        name = "리뷰 API",
        description = "상품 리뷰 조회, 등록, 수정, 삭제 및 통계 API"
)
public class ReviewController {

    private final ReviewService reviewService;
    private final S3UploadService s3UploadService;

    @Operation(
            summary = "상품 리뷰 목록 조회",
            description = "상품 ID 기준으로 리뷰 목록을 페이징 및 정렬 기준에 따라 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "상품 없음")
    })
    @GetMapping("/product/{productId}")
    public ResponseEntity<PageResponseDTO<ReviewDTO>> getList(
            @Parameter(description = "상품 ID", example = "1")
            @PathVariable("productId") Long productId,

            @Parameter(description = "정렬 기준 (latest | rating)", example = "latest")
            @RequestParam(defaultValue = "latest") String sort, //기본 최신순

            @Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
            @RequestParam(defaultValue = "1") int page,

            @Parameter(description = "페이지당 조회 개수", example = "10")
            @RequestParam(defaultValue = "10") int size
            ) {
        log.info("리뷰 목록 page : {}, size : {}, 정렬 기준 : {}", page, size, sort);

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
            .page(page)
            .size(size)
            .build();

        PageResponseDTO<ReviewDTO> reviews = reviewService.getList(productId, sort, pageRequestDTO);

        log.info("리뷰 목록 : {}", reviews);

        return ResponseEntity.ok(reviews);
    }



    @Operation(
            summary = "나의 리뷰 조회",
            description = "로그인한 사용자의 리뷰 목록을 조회합니다."
    )
    @GetMapping("/user")
    public ResponseEntity<PageResponseDTO<ReviewDTO>> getMyReviews(
            @Parameter(description = "페이지 번호", example = "1")
            @RequestParam(defaultValue = "1") int page,

            @Parameter(description = "페이지당 조회 개수", example = "10")
            @RequestParam(defaultValue = "10") int size
          ) {
        log.info("나의 리뷰 목록 page : {}, size : {}", page, size);

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
            .page(page)
            .size(size)
            .build();

        PageResponseDTO<ReviewDTO> reviews = reviewService.getListByUser(pageRequestDTO);

        log.info("나의 리뷰 목록 : {}", reviews);
        return ResponseEntity.ok(reviews);
    }



    @Operation(
            summary = "리뷰 등록",
            description = "리뷰 정보와 이미지 파일을 함께 업로드합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping(
            value = "/add",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> register(
            @Parameter(description = "리뷰 정보")
            @RequestPart("review") ReviewDTO dto,

            @Parameter(description = "리뷰 이미지 파일")
            @RequestPart(value = "reviewImage", required = false) List<MultipartFile> reviewImage
    ){
        log.info("리뷰 등록 dto : {}", dto);
        log.info("register 리뷰 이미지 : {}", reviewImage);

        Long id = reviewService.register(dto);
        log.info("reviewService.register : {}", id);

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

        return ResponseEntity.ok("리뷰 등록 성공");
    }



    @Operation(
            summary = "리뷰 수정",
            description = "기존 리뷰 내용을 수정하고 이미지를 추가합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "리뷰 없음")
    })
    @PutMapping(
            value = "/modify/{reviewId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> modify(
            @Parameter(description = "리뷰 ID", example = "1")
            @PathVariable("reviewId") Long reviewId,

            @Parameter(description = "리뷰 정보")
            @RequestPart("review") ReviewDTO dto,

            @Parameter(description = "추가할 리뷰 이미지 파일")
            @RequestPart(value = "reviewImage", required = false) List<MultipartFile> reviewImage
    ){
        log.info("수정하려는 reviewId : {}", reviewId);
        log.info("modify 리뷰 dto : {}", dto);
        log.info("modify 리뷰 이미지 : {}", reviewImage);

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

        return ResponseEntity.ok("리뷰 수정 성공");
    }



    @Operation(
            summary = "리뷰 삭제",
            description = "리뷰 ID 기준으로 리뷰를 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "리뷰 없음")
    })
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> remove(
            @Parameter(description = "리뷰 ID", example = "1")
            @PathVariable("reviewId") Long reviewId
    ) {
        log.info("삭제 reviewId : {}", reviewId);

        reviewService.remove(reviewId);

        return ResponseEntity.ok("리뷰 삭제 성공");
    }



    @Operation(
            summary = "상품 리뷰 평균 별점 조회",
            description = "상품에 대한 리뷰 평균 별점을 조회합니다."
    )
    @GetMapping("/product/{productId}/avg")
    public ResponseEntity<Double> getAvgRating(
            @Parameter(description = "상품 ID", example = "5")
            @PathVariable Long productId
    ){
        log.info("product review avgRating productId : {}", productId);

        double avgRating = reviewService.getAvgRating(productId);

        log.info("avgRating : {}", avgRating);
        return ResponseEntity.ok(avgRating);
    }



    @Operation(
            summary = "상품 리뷰 개수 조회",
            description = "상품에 등록된 전체 리뷰 개수를 조회합니다."
    )
    @GetMapping("/product/{productId}/count")
    public ResponseEntity<Integer> getReviewCount(
            @Parameter(description = "상품 ID", example = "5")
            @PathVariable Long productId
    ){
        log.info("reviewCount productId : {}", productId);

        int reviewCount = reviewService.getReviewTotalCount(productId);

        log.info("reviewCount : {}", reviewCount);
        return ResponseEntity.ok(reviewCount);
    }



    @Operation(
            summary = "별점별 리뷰 개수 조회",
            description = "상품의 특정 별점(1~5점)에 해당하는 리뷰 개수를 조회합니다."
    )
    @GetMapping("/product/{productId}/{rating}/count")
    public ResponseEntity<Integer> getRatingByCount(
            @Parameter(description = "상품 ID", example = "5")
            @PathVariable Long productId,

            @Parameter(description = "별점 (1~5)", example = "5")
            @PathVariable Integer rating
    ){
        log.info("productId : {}, 상품의 별점(5,4,3,2,1) : {}", productId, rating);

        int ratingBYCount = reviewService.getRatingByCount(productId, rating);

        log.info("ratingByCount : {}", ratingBYCount);
        return ResponseEntity.ok(ratingBYCount);
    }



    @Operation(
            summary = "긍정 리뷰 개수 조회",
            description = "상품의 긍정 리뷰 개수를 조회합니다."
    )
    @GetMapping("/product/{productId}/positive")
    public ResponseEntity<Integer> getPositiveReview(
            @Parameter(description = "상품 ID", example = "5")
            @PathVariable Long productId
    ){
        log.info("positiveReview productId : {}", productId);

        int positiveReview = reviewService.getPositiveReview(productId);

        log.info("positiveReview : {}", positiveReview);
        return ResponseEntity.ok(positiveReview);
    }

}
