package kr.kro.moonlightmoist.shopapi.address.dto;

import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressDTO {
    private Long id;
    private Long userId;
    private String addressName;
    private String receiverName;
    private String receiverPhone;
    private String postalCode;
    private String streetAddress;
    private String detailedAddress;
    private boolean defaultAddress;
    private String deliveryRequest;
}
