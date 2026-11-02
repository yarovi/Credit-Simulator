package org.yasmani.io.creditsimulator.shared.infrastructure.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
public class CreditPersistenceConfig {

  @Bean("creditDataSource")
  @ConfigurationProperties(prefix = "app.datasource.credit")
  public HikariDataSource creditDataSource() {
    return new HikariDataSource();
  }
}
