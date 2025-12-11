package kr.kro.moonlightmoist.shopapi.common.config;

import lombok.Getter;
import lombok.Setter;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
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

    @Bean
    public DefaultMessageService defaultMessageService() {
        return new DefaultMessageService(this.apiKey, this.apiSecret, "https://api.coolsms.co.kr");
    }

}
