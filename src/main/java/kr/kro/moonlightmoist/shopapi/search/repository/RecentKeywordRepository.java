package kr.kro.moonlightmoist.shopapi.search.repository;

import kr.kro.moonlightmoist.shopapi.search.domain.RecentKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecentKeywordRepository extends JpaRepository<RecentKeyword, Long> {

    //회원 ID를 기준으로 최근 검색한 키워드 10개
    @Query(value = "SELECT * FROM recent_search_keyword " +
                   //recent_searches 테이블의 모든 컬럼을 가져온다
            "WHERE user_id = :userId AND is_visible = true " +
            //회원ID가 userId인 검색 기록만 필터링
            "ORDER BY created_at DESC " +
            //최신 순으로 정렬
            "LIMIT 20", nativeQuery = true) //최대 20개만 가져옴
            //중복이 있어도 충분히 가져와서 Service에서 중복 제거 후 최근 10개만 보여주기 위해 20개를 가져온다
    List<RecentKeyword> findByUserIdRecent(@Param("userId") Long userId);

    //비회원 식별자를 기준으로 최근 검색한 키워드 10개
    @Query(value = "SELECT * FROM recent_search_keyword " +
            "WHERE guest_Id = :guestId AND is_visible = true " +
            "ORDER BY created_at DESC " +
            "LIMIT 20", nativeQuery = true)
    List<RecentKeyword> findByGuestIdRecent(@Param("guestId") String guestId);

}
