package com.stylefeng.guns.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication(scanBasePackages = {"com.stylefeng.guns", "com.md.*"})
public class GunsRestApplication {
	
	// CORS
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addExposedHeader("x-auth-token");
        corsConfiguration.addExposedHeader("x-total-count");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        System.out.println("*********************************过滤器被使用**************************");
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    public static void main(String[] args) {
        SpringApplication.run(GunsRestApplication.class, args);
    }
}
