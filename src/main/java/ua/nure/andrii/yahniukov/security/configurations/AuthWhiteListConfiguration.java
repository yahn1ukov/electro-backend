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
                "/api/v1/auth/login",
                "/api/v1/auth/register/user",
                "/api/v1/auth/register/charger/partner",
                "/api/v1/auth/register/station/partner",
                "/api/v1/iot/create/car",
                "/api/v1/station/get/all",
                "/api/v1/charger/get/all",
                "/api/v1/charger/change/isCharging/{chargerName}",
                "/api/v1/charger/change/isBroken/{chargerName}",
                "/api/v1/iot/station/{latitude}/{longitude}/{name}/{model}/{radius}",
                "/api/v1/iot/charger/{latitude}/{longitude}/{percentOfBattery}/{typeConnector}/{radius}"

        };
    }
}
