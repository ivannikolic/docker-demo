package rs.codlr.imager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SpringBootApplication
@ComponentScan(basePackageClasses = ImagerCoreApplication.class)
public class ImagerCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImagerCoreApplication.class, args);
    }

}
