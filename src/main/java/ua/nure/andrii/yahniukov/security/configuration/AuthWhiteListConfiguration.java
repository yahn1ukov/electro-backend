package ua.nure.andrii.yahniukov.security.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthWhiteListConfiguration {
    @Bean
    @Qualifier("authWhiteList")
    public String[] getAuthWhiteList() {
        return new String[]{
                "/swagger-resources",
                "/documentation/swagger-ui",
                "/swagger-resources/**",
                "/swagger-resources/configuration/ui",
                "/swagger-resources/configuration/security",
                "/swagger-ui",
                "/swagger-ui/index.html",
                "/swagger-ui/**",
                "/favicon.ico",
                "/error",
                "/webjars/**",
                "/v2/api-docs",
                "/swagger-ui.html",
                "/api/swagger-ui.html",
                "/api/v1/authentication/login",
                "/api/v1/authentication/registration/users",
                "/api/v1/authentication/registration/users/chargers",
                "/api/v1/authentication/registration/users/stations",
                "/api/v1/cars/create",
                "/api/v1/cars/vin-codes/{vinCode}",
                "/api/v1/cars/vin-codes/{vinCode}/update",
                "/api/v1/cars/vin-codes/{vinCode}/chargers",
                "/api/v1/cars/vin-codes/{vinCode}/stations"
        };
    }
}