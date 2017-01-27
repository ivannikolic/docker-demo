package rs.codlr.imager.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import rs.codlr.imager.model.EntityPackage;
import rs.codlr.imager.repository.RepositoryPackage;

/**
 * @ivan
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = RepositoryPackage.class)
@EntityScan(basePackageClasses = EntityPackage.class)
public class JpaConfig {
}
