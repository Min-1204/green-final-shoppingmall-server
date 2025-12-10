package kr.kro.moonlightmoist.shopapi.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "coolsms")
@Getter
@Setter
public class CoolSmsConfig {

    private String apiKey;
    private String apiSecret;
    private String sender;
    private boolean enabled;
}
