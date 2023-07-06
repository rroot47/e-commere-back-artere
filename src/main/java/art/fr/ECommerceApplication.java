package art.fr;

import art.fr.constants.ArtereConstant;
import art.fr.security.RsaKeysConfig;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeysConfig.class)
@OpenAPIDefinition(
        info = @Info(title = ArtereConstant.ARTERE_API_TITLE),
        servers = @Server(
                url = ArtereConstant.ARTERE_API_BASE_URL
        )
)
@SecurityScheme(name = ArtereConstant.BEARER_AUTHORIZaTION, scheme = ArtereConstant.SCHEME_BEARER, type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER, bearerFormat=ArtereConstant.BEARER_FORMAT)
public class ECommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
