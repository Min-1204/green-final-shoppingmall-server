package kr.kro.moonlightmoist.shopapi.product.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RestockOptionReq {
    private Long optionId;
    private int newStock;
}
