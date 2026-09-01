package com.library.apigateway.config;

import com.library.apigateway.filter.JwtWebMvcFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.server.mvc.filter.FilterSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

@Configuration
public class GatewayConfig {

    private static JwtWebMvcFilter jwtWebMvcFilterInstance;

    @Autowired
    public void setJwtWebMvcFilterInstance(JwtWebMvcFilter filter) {
        GatewayConfig.jwtWebMvcFilterInstance = filter;
    }

    @Bean
    public FilterSupplier jwtFilterSupplier() {

        return new FilterSupplier() {

            @Override
            public Collection<Method> get() {

                try {
                    return List.of(GatewayConfig.class.getMethod("jwtFilter"));
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    public static HandlerFilterFunction<ServerResponse, ServerResponse> jwtFilter() {

        return jwtWebMvcFilterInstance;
    }
}
