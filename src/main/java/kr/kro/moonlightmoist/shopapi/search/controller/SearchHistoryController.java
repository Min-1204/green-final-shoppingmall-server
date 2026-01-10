package kr.kro.moonlightmoist.shopapi.search.controller;

import kr.kro.moonlightmoist.shopapi.product.dto.ProductResForList;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductSearchCondition;
import kr.kro.moonlightmoist.shopapi.product.service.ProductService;
import kr.kro.moonlightmoist.shopapi.review.dto.PageRequestDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.PageResponseDTO;
import kr.kro.moonlightmoist.shopapi.search.dto.SearchPopularKeywordResponseDTO;
import kr.kro.moonlightmoist.shopapi.search.dto.SearchRecentKeywordResponseDTO;
import kr.kro.moonlightmoist.shopapi.search.service.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/search")
@CrossOrigin(origins = "http://localhost:5173")
public class SearchHistoryController {

    private final SearchHistoryService searchHistoryService;
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Void> searchAdd(
            @RequestParam String keyword,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String guestId
    ) {
        searchHistoryService.searchAdd(userId, guestId, keyword);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recent")
    public ResponseEntity<List<SearchRecentKeywordResponseDTO>> getRecentKeywords(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String guestId
    ) {
        List<SearchRecentKeywordResponseDTO> recentList =
                searchHistoryService.getRecentKeywordList(userId, guestId);

        return ResponseEntity.ok(recentList);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<SearchPopularKeywordResponseDTO>> getPopularKeywords() {

        List<SearchPopularKeywordResponseDTO> searchPopularKeywordResponseDTO =
                searchHistoryService.getPoularKeywordList();

        return ResponseEntity.ok(searchPopularKeywordResponseDTO);
    }

    @GetMapping("/product")
    public ResponseEntity<PageResponseDTO<ProductResForList>> getProductResForList(
            @RequestParam("keyword") String keyword,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        log.info("keyword => {}, page => {}, size => {}", keyword, page, size);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Product Search"); // 측정 시작

        ProductSearchCondition condition = new ProductSearchCondition();
        condition.setSearchKeywords(keyword);

        PageRequestDTO pageRequest = PageRequestDTO.builder().page(page).size(size).build();

        PageResponseDTO<ProductResForList> result = productService.searchProductsByConditionWithPaging(condition, pageRequest);

        stopWatch.stop(); // 측정 종료
        log.info(stopWatch.prettyPrint()); // 결과 출력 (총 시간 및 상세 내역)

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/recent/one")
    public ResponseEntity<Void> deleteOneRecentKeyword(
            @RequestParam String keyword,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String guestId
    ) {
        searchHistoryService.deleteOneRecentKeyword(userId, guestId, keyword);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/recent/all")
    public ResponseEntity<Void> deleteAllRecentKeyword(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String guestId
    ) {
        searchHistoryService.deleteAllRecentKeywords(userId, guestId);
        return ResponseEntity.ok().build();
    }
}
