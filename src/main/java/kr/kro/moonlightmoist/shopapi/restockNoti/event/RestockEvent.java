package kr.kro.moonlightmoist.shopapi.restockNoti.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestockEvent {
    private final Long optionId;
    private final String optionName;
}
