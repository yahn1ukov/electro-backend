package ua.nure.andrii.yahniukov.security.configurations;

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
                "/api/v1/authentication/logout",
                "/api/v1/cars/create",
                "/api/v1/stations/get/all",
                "/api/v1/stations/{name}/get",
                "/api/v1/chargers/get/all",
                "/api/v1/chargers/{code}/get",
                "/api/v1/chargers/{code}/change/is-charging",
                "/api/v1/chargers/{code}/change/is-broken",
                "/api/v1/stations/{latitude}/{longitude}/{name}/{model}/{radius}",
                "/api/v1/chargers/{latitude}/{longitude}/{percentOfBattery}/{typeConnector}/{radius}"
        };
    }
}
