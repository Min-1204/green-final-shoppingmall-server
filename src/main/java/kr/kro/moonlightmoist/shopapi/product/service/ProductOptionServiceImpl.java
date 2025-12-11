package kr.kro.moonlightmoist.shopapi.product.service;

import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import kr.kro.moonlightmoist.shopapi.product.dto.RestockOptionReq;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductOptionRepository;
import kr.kro.moonlightmoist.shopapi.restockNoti.event.RestockEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductOptionServiceImpl implements ProductOptionService{

    private final ProductOptionRepository productOptionRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void restockOptions(List<RestockOptionReq> dto) {
        List<Long> ids = dto.stream().map(o -> o.getOptionId()).toList();
        List<ProductOption> options = productOptionRepository.findByIds(ids);

        Map<Long, Integer> optionMap = dto.stream().collect(Collectors.toMap(
                o -> o.getOptionId(),
                o -> o.getNewStock()
        ));

        for (ProductOption option : options) {
            int oldStock = option.getCurrentStock();
            int newStock = optionMap.get(option.getId());

            option.setCurrentStock(newStock);

            if (oldStock ==0 && newStock >=1 ) {
                //이벤트 발행
                applicationEventPublisher.publishEvent(new RestockEvent(option.getId(), option.getOptionName()));
            }
        }
    }

}
