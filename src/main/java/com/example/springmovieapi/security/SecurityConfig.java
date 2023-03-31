package com.example.springmovieapi.security;

import com.example.springmovieapi.security.jwt.handler.CustomAccessDeniedHandler;
import com.example.springmovieapi.security.jwt.handler.JwtAuthEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.example.springmovieapi.security.jwt.JwtRequestFilter;

/**
 * Configuration class for Spring Security settings in the application.
 * Configures authentication, authorization, and CORS settings.
 * Uses JWT tokens for authentication and includes a filter to check the JWT token in incoming requests.
 * Allows access to certain endpoints without authentication and requires authentication for all other endpoints.
 * Includes a PasswordEncoder for encoding and decoding passwords.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides an instance of the AuthenticationManager, which is responsible for validating user credentials
     * and creating an Authentication object that represents the authenticated principal.
     * @param authenticationConfiguration the AuthenticationConfiguration to use for creating the AuthenticationManager
     * @return an AuthenticationManager object
     * @throws Exception if an error occurs while creating the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Provides an instance of the JwtRequestFilter, which checks the JWT token in incoming requests.
     * @return a JwtRequestFilter object
     */
    @Bean
    public JwtRequestFilter authenticationJwtTokenFilter() {
        return new JwtRequestFilter();
    }

    /**
     * Configures the filter chain for handling incoming requests, including CORS and CSRF settings.
     * Allows access to certain endpoints without authentication and requires authentication for all other endpoints.
     * Adds the JwtRequestFilter to the filter chain.
     * @param http a SecurityFilterChain object
     * @return the HttpSecurity object to configure
     * @throws Exception if an error occurs while configuring the HttpSecurity object
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).accessDeniedHandler(accessDeniedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests().requestMatchers("/api/auth/**", "/swagger-ui/**","/v3/api-docs/**").permitAll()
                .requestMatchers("/").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /*
    /**
     * Configures CORS settings for incoming requests.
     * @return a WebMvcConfigurer object
     */
    /*
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH")
                        .allowedHeaders("Access-Control-Allow-Origin", "X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization")
                        .allowCredentials(true)
                        .allowedOriginPatterns("http://localhost:4200");
            }
        };
    } */

}