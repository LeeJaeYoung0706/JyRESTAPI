package rest.JyRESTAPI.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "JaeYoung REST API",
                description = "Spring 공부 API",
                version = "v1",
                contact = @Contact(name = "JaeYoung", email = "support@example.com"),
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html")
        )
)
public class SwaggerConfig {

        @Bean
        public GroupedOpenApi testApiGroup() {
                return GroupedOpenApi.builder()
                        .group("테스트")
                        .displayName("테스트 기능")
                        .pathsToMatch("/test/*")
                        .build();
        }
}
