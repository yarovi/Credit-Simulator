package org.yasmani.io.creditsimulator.shared.infrastructure.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Map;

@EnableJpaRepositories(
    basePackages = "org.yasmani.io.creditsimulator.customer.infrastructure.persistence",
    entityManagerFactoryRef = "customerEntityManagerFactory",
    transactionManagerRef = "customerTransactionManager"
)

@Configuration(proxyBeanMethods = false)
public class CustomerPersistenceConfig {
  @Bean("customerDataSource")
  @Primary
  @ConfigurationProperties(prefix = "app.datasource.customer")
  public HikariDataSource customerDataSource() {
    return new HikariDataSource();
  }

  @Bean("customerEntityManagerFactory")
  @Primary
  @DependsOn("customerFlywayInitializer")
  public LocalContainerEntityManagerFactoryBean customerEntityManagerFactory(
      @Qualifier("customerDataSource") HikariDataSource dataSource) {

    var factory = new LocalContainerEntityManagerFactoryBean();

    factory.setDataSource(dataSource);
    factory.setPackagesToScan(
        "org.yasmani.io.creditsimulator.customer.infrastructure.persistence"
    );
    factory.setPersistenceUnitName("customer");
    factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    factory.setJpaPropertyMap(Map.of(
        "hibernate.hbm2ddl.auto", "validate"
    ));

    return factory;
  }

  @Bean("customerTransactionManager")
  @Primary
  public JpaTransactionManager customerTransactionManager(
      @Qualifier("customerEntityManagerFactory")
      EntityManagerFactory entityManagerFactory) {

    return new JpaTransactionManager(entityManagerFactory);
  }
}
