package kr.kro.moonlightmoist.shopapi.product.service;

import kr.kro.moonlightmoist.shopapi.product.dto.RestockOptionReq;

import java.util.List;

public interface ProductOptionService {
    void restockOptions(List<RestockOptionReq> dto);
}
