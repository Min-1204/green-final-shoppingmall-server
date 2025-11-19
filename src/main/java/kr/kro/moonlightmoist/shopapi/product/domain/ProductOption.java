package kr.kro.moonlightmoist.shopapi.product.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "product_options")
public class ProductOption extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(unique = true, nullable = false)
    private String optionName;

    @Column(nullable = false)
    private int purchasePrice;

    @Column(nullable = false)
    private int sellingPrice;

    @Column(nullable = false)
    private int currentStock;

    @Column(nullable = false)
    private int initialStock;

    @Column(nullable = false)
    private int safetyStock;

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = false)
    private int displayOrder;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    public void deleteProductOption() {
        this.deleted = true;
    }
}

