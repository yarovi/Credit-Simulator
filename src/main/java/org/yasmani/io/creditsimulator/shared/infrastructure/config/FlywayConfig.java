package org.yasmani.io.creditsimulator.shared.infrastructure.config;


import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.flyway.autoconfigure.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
public class FlywayConfig {

  @Bean("customerFlyway")
  public Flyway customerFlyway(
      @Qualifier("customerDataSource") DataSource dataSource) {

    return Flyway.configure()
        .dataSource(dataSource)
        .locations("classpath:db/migration/customer")
        .validateMigrationNaming(true)
        .load();
  }

  @Bean("creditFlyway")
  public Flyway creditFlyway(
      @Qualifier("creditDataSource") DataSource dataSource) {

    return Flyway.configure()
        .dataSource(dataSource)
        .locations("classpath:db/migration/credit")
        .load();
  }

  @Bean
  public org.springframework.boot.ApplicationRunner migrateDatabases(
      @Qualifier("customerFlyway") Flyway customerFlyway,
      @Qualifier("creditFlyway") Flyway creditFlyway) {

    return args -> {
      customerFlyway.migrate();
      creditFlyway.migrate();
    };
  }

  @Bean("customerFlywayInitializer")
  public FlywayMigrationInitializer customerFlywayInitializer(
      @Qualifier("customerFlyway") Flyway flyway) {
    return new FlywayMigrationInitializer(flyway);
  }

  @Bean("creditFlywayInitializer")
  public FlywayMigrationInitializer creditFlywayInitializer(
      @Qualifier("creditFlyway") Flyway flyway) {
    return new FlywayMigrationInitializer(flyway);
  }

}
