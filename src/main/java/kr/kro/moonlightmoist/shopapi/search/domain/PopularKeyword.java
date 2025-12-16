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

    //인기 검색어 집계 엔티티
    //검색어(keyword) 자체가 도메인 상에서 유일한 식별자이기 때문에
    //별도의 Long 타입 key를 두지 않고 keyword를 pk로 사용

    //동일한 검색어(keyword)는 하나의 행으로만 저장되고,
    //중복 검색 시에는 검색 횟수(count)만 증가

    //이 테이블은 단독 집계용 테이블이며, 조회 및 count 증가만 빈번한 구조

    @Id
    @Column(length = 150)
    private String keyword;
    //검색어 문자열 자체를 식별자로 사용
    //검색어는 도메인 상에서 유일하다고 판단되는 자연키(Natural Key)
    //자연키는 DB 제약이 없으나 pk로 지정해서 중복 방지

    @Column(nullable = false)
    private int count;
    //해당 검색어의 누적 검색 횟수
    //동일 keyword에 대해 검색할 때마다 증가

    @Column(nullable = false)
    private LocalDateTime lastSearchedAt;
    //해당 검색어가 마지막으로 검색된 시각
    //인기 검색어 최신성 판단
}

