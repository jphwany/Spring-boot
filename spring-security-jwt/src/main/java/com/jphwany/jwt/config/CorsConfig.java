package com.jphwany.jwt.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/api/**", config);

        return new CorsFilter(source);
    }
}
/*
    .setAllowCredentials()
    서버가 응답할 때 json을 자바스크립트에서 처리할 수 있게 설정

    .addAllowedOrigin(”*”)
    모든 ip에 응답 허용

    .addAllowedHeader(”*”)
    모든 header에 응답 허용

    .addAllowedMethod(”*”)
    모든 post, get, patch, delete 요청 허용

 */
