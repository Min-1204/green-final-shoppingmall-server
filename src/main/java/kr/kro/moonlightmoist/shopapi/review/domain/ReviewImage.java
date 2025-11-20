package kr.kro.moonlightmoist.shopapi.review.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class ReviewImage {

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private int imageOrder;

    public void setOrder(int imageOrder) {
        this.imageOrder = imageOrder;
    }

}
