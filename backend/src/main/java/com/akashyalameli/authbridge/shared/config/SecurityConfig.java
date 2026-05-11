package com.akashyalameli.authbridge.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.akashyalameli.authbridge.auth.infrastructure.JwtAuthenticationFilter;

//import jakarta.servlet.http.HttpServletResponse; //Yet to solve the issue between 401s and 403s

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/register",
                                "/api/auth/register-admin",
                                "/api/auth/login",
                                "/api/auth/refresh",
                                "/health",
                                "/swagger",
                                "/swagger/**",
                                "/swagger-ui/**",
                                "/api/docs/**",
                                "/api/docs/**"
                        ).permitAll()
                        .requestMatchers("/api/tenant/**").hasRole("ADMIN")
                        .requestMatchers("/api/identity/**").hasAnyRole("USER", "ADMIN", "AUDITOR")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtFilter,
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class
                )
                /*.exceptionHandling(ex -> ex //Yet to solve the issue between 401s and 403s
                        .authenticationEntryPoint(
                                (req, res, authEx) ->
                                res.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                        ).accessDeniedHandler(
                                (req, res, accessDeniedEx) ->
                                res.sendError(HttpServletResponse.SC_FORBIDDEN)
                        )
                )
                .anonymous(anonymous -> anonymous.disable())*/;

        return http.build();
    }
}
