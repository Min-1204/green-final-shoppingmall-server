package kr.kro.moonlightmoist.shopapi.search.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "popular_search_keywords")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PopularKeyword {

    @Id
    @Column(length = 150)
    private String keyword;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private LocalDateTime lastSearchedAt;
}
