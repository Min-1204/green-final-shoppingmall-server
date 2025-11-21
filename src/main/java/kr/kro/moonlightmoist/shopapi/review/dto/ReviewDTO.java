package kr.kro.moonlightmoist.shopapi.review.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import lombok.*;
import org.hibernate.annotations.Check;
import org.springframework.web.multipart.MultipartFile;

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
    private Long productId;

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();

    @Builder.Default
    private List<String> uploadFileNames = new ArrayList<>();

}
