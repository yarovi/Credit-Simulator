package org.yasmani.io.creditsimulator.access.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http
  ) throws Exception {

    return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                HttpMethod.POST,
                "/api/v1/creditos/consultas"
            ).permitAll()
            .requestMatchers(
                HttpMethod.GET,
                "/api/v1/creditos/ofertas"
            ).permitAll()
            .requestMatchers(
                HttpMethod.POST,
                "/api/v1/creditos/ofertas/selecciones"
            ).permitAll()
            .anyRequest().denyAll()
        )
        .build();
  }
}
