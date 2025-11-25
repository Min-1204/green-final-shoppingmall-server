package kr.kro.moonlightmoist.shopapi.address.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_addresses")
public class UserAddress extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    @Column(nullable = false)
    private String addressName;
    @Column(nullable = false)
    private String receiverName;
    @Column(nullable = false)
    private String receiverPhone;
    @Column(nullable = false)
    private String postalCode;
    @Column(nullable = false)
    private String streetAddress;
    @Column(nullable = true)
    private String detailedAddress;

    @Column(name="is_default",nullable = false)
    private boolean defaultAddress;

    @Column(nullable = true)
    private String deliveryRequest;


}
