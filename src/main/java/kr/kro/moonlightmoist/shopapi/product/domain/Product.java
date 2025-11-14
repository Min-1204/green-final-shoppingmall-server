package kr.kro.moonlightmoist.shopapi.product.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String productCode;
    private String searchKeywords;

    @Enumerated(EnumType.STRING)
    private ExposureStatus exposureStatus;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    private String description;

    @Column(name = "is_cancelable")
    private boolean cancelable;

    @Column(name = "is_deleted")
    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
