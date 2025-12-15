package kr.kro.moonlightmoist.shopapi.search.repository;

import kr.kro.moonlightmoist.shopapi.search.domain.PopularKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PopularKeywordRepository extends JpaRepository<PopularKeyword, String> {

    List<PopularKeyword> findTop10ByOrderByCountDesc();

    Optional<PopularKeyword> findByKeyword(String keyword);
}
