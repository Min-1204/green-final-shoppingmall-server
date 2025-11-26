package kr.kro.moonlightmoist.shopapi.review.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
public class ReviewDTO {

    private Long id;
    private String content;
    private int rating;
    private Long userId;
    private String loginId;
    private Long productId;
    private LocalDateTime createdAt;

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();

    @Builder.Default
    private List<String> uploadFileNames = new ArrayList<>();

}
