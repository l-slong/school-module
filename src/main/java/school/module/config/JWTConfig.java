package school.module.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Limgmk
 * @time 20-9-19 下午6:29
 * @description read and set JWT config
 */

@ConfigurationProperties(prefix = "audience")
@Component
public class JWTConfig {

    @Getter
    @Setter
    private String clientId;

    @Getter
    @Setter
    private String base64Secret;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int expiresSecond;
}
