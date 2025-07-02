package com.ziad.task.config;

import com.ziad.task.model.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            CustomUsernamePwdAuthenticationProvider authenticationProvider,
            CustomUserDetailsService userDetailsService,
            JWTTokenValidatorFilter jwtTokenValidatorFilter,
            CustomBasicAuthenticationEntryPointHandler entryPointHandler
    ) throws Exception {
        var swagger = new String[]{"/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/api-docs/**"};
        var anyUser = new String[]{"/v1/users/", "/v1/users/**"};
        var anyAdmin = new String[]{"/v1/tasks", "/v1/tasks/**"};
        var auth = new String[]{"/v1/auth/**"};
        http
                .userDetailsService(userDetailsService)
                .authenticationProvider(authenticationProvider)
                .securityContext(contextConfig -> contextConfig.requireExplicitSave(false))
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsConfig -> corsConfig.configurationSource(request -> {
                    var config = new CorsConfiguration();
                    config.setAllowCredentials(Boolean.TRUE);
                    config.setAllowedOrigins(List.of("http://localhost:3000"));
                    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    config.setMaxAge(3600L);
                    config.setExposedHeaders(List.of(HttpHeaders.AUTHORIZATION));
                    return config;
                }))
                .csrf(csrfConfig -> csrfConfig
                        // CSRF handler to support token as a request attribute
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                        .ignoringRequestMatchers(auth)
                        .ignoringRequestMatchers(anyUser)
                        .ignoringRequestMatchers(anyAdmin)
                        .ignoringRequestMatchers(swagger)
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .addFilterBefore(jwtTokenValidatorFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(
                        (requests) -> requests
                                .requestMatchers(anyUser).permitAll()
                                .requestMatchers(auth).permitAll()
                                .requestMatchers(anyAdmin).hasAnyRole(User.UserRole.ADMIN.toString(), User.UserRole.USER.toString())
                                .requestMatchers(anyAdmin).hasAuthority(User.UserRole.ADMIN.toString())
                                .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(hbc -> hbc.authenticationEntryPoint(entryPointHandler));
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
