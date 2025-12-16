package kr.kro.moonlightmoist.shopapi.search.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductResForList;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductSearchCondition;
import kr.kro.moonlightmoist.shopapi.product.service.ProductService;
import kr.kro.moonlightmoist.shopapi.review.dto.PageResponseDTO;
import kr.kro.moonlightmoist.shopapi.search.dto.SearchPopularKeywordResponseDTO;
import kr.kro.moonlightmoist.shopapi.search.dto.SearchRecentKeywordResponseDTO;
import kr.kro.moonlightmoist.shopapi.search.service.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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

        ProductSearchCondition condition = new ProductSearchCondition();
        condition.setSearchKeywords(keyword);

        PageResponseDTO<ProductResForList> result = productService.searchProductsByConditionWithPaging(condition, page, size);

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
