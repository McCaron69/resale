package ee.tptlive.arturivushkin.resale.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH", "OPTION")
        .allowedHeaders("No-Auth","Authorization","Cache-Control", "Content-Type", "app-id", "platform", "sentry-trace","Access-Control-Allow-Origin");
  }
}
