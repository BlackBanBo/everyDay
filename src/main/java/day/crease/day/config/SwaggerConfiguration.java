package day.crease.day.config;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/*").addResourceLocations("classpath:/META-INF/resources/");
    }

    //设置监控路径
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).produces(Sets.newHashSet("application/json"))
                .consumes(Sets.newHashSet("application/json")).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("day.crease.day")).paths(PathSelectors.any())
                .build();
    }
    /**
     * ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("微光点亮星辰")
                .version("0.0.1").description("微光点亮星辰 接口文档（Swagger2）").build();
    }
}
