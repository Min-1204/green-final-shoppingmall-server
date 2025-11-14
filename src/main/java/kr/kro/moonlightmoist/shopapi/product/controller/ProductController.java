package kr.kro.moonlightmoist.shopapi.product.controller;

import kr.kro.moonlightmoist.shopapi.product.dto.ProductRequest;
import kr.kro.moonlightmoist.shopapi.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/product")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {org.springframework.web.bind.annotation.RequestMethod.GET,
                org.springframework.web.bind.annotation.RequestMethod.POST,
                org.springframework.web.bind.annotation.RequestMethod.PUT,
                org.springframework.web.bind.annotation.RequestMethod.DELETE,
                org.springframework.web.bind.annotation.RequestMethod.OPTIONS})
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping("/register")
    public ResponseEntity<Map<String,Long>> productRegister(@RequestBody ProductRequest request) {

        System.out.println("request = " + request);

        Long id = productService.register(request);

        System.out.println("id = " + id);

        return ResponseEntity.ok(Map.of("result", id));
    }

}
