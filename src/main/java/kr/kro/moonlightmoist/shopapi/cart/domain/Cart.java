package kr.kro.moonlightmoist.shopapi.cart.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Cart extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CartProduct> cartProducts = new ArrayList<>();

    public void addCartProduct(CartProduct cartProduct) {
        cartProducts.add(cartProduct);
    }
    public void removeCartProduct(CartProduct cartProduct){
        cartProducts.remove(cartProduct);
    }


}
