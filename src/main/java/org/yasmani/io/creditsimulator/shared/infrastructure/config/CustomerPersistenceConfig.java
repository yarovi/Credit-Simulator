package org.yasmani.io.creditsimulator.shared.infrastructure.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration(proxyBeanMethods = false)
public class CustomerPersistenceConfig {
  @Bean("customerDataSource")
  @Primary
  @ConfigurationProperties(prefix = "app.datasource.customer")
  public HikariDataSource customerDataSource() {
    return new HikariDataSource();
  }
}
