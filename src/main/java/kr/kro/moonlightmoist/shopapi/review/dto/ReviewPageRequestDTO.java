package kr.kro.moonlightmoist.shopapi.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewPageRequestDTO {
    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;
}
