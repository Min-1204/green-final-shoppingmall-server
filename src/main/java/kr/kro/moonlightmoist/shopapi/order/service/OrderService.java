package kr.kro.moonlightmoist.shopapi.order.service;

import kr.kro.moonlightmoist.shopapi.order.domain.Order;
import kr.kro.moonlightmoist.shopapi.order.domain.OrderProduct;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderProductRequestDTO;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderRequestDTO;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    String register(OrderRequestDTO dto, Long userId);

}
