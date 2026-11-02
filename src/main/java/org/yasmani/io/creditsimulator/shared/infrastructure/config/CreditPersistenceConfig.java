package org.yasmani.io.creditsimulator.shared.infrastructure.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Map;

@EnableJpaRepositories(
    basePackages = {
        "org.yasmani.io.creditsimulator.credit.infrastructure.persistence",
        "org.yasmani.io.creditsimulator.account.infrastructure.persistence"
    },
    entityManagerFactoryRef = "creditEntityManagerFactory",
    transactionManagerRef = "creditTransactionManager"
)
@Configuration(proxyBeanMethods = false)
public class CreditPersistenceConfig {

  @Bean("creditDataSource")
  @ConfigurationProperties(prefix = "app.datasource.credit")
  public HikariDataSource creditDataSource() {
    return new HikariDataSource();
  }

  @Bean("creditEntityManagerFactory")
  @DependsOn("creditFlywayInitializer")
  public LocalContainerEntityManagerFactoryBean creditEntityManagerFactory(
      @Qualifier("creditDataSource") HikariDataSource dataSource) {

    var factory = new LocalContainerEntityManagerFactoryBean();

    factory.setDataSource(dataSource);
    factory.setPackagesToScan(
        "org.yasmani.io.creditsimulator.credit.infrastructure.persistence",
        "org.yasmani.io.creditsimulator.account.infrastructure.persistence"
    );
    factory.setPersistenceUnitName("credit");
    factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    factory.setJpaPropertyMap(Map.of(
        "hibernate.hbm2ddl.auto", "validate"
    ));

    return factory;
  }

  @Bean("creditTransactionManager")
  public JpaTransactionManager creditTransactionManager(
      @Qualifier("creditEntityManagerFactory")
      EntityManagerFactory entityManagerFactory) {

    return new JpaTransactionManager(entityManagerFactory);
  }
}
