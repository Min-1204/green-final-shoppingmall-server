package kr.kro.moonlightmoist.shopapi.review.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewImageUrlDTO {
    private List<String> imageUrls;
}
