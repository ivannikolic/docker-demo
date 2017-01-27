package rs.codlr.imager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ivan
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Profile("local")
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurerLOCAL() {
        Resource[] LOCAL_PROPERTIES = new ClassPathResource[]{
                new ClassPathResource("application.properties"),
                new ClassPathResource("application-local.properties"),
        };
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        pspc.setLocations(LOCAL_PROPERTIES);
        return pspc;
    }
}
