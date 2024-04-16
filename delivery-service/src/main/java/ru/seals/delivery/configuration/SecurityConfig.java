package ru.seals.delivery.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import ru.seals.delivery.security.JwtAuthConverter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthConverter jwtAuthConverter;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfig(JwtAuthConverter jwtAuthConverter,
                          @Qualifier("delegatedAuthenticationEntryPoint")
                          AuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtAuthConverter = jwtAuthConverter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    { return authenticationConfiguration.getAuthenticationManager();}
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        return http
                .authorizeHttpRequests(customizer -> customizer
                        .requestMatchers("/api/delivery-service/user/**",
                                "/api/delivery-service/user/tracking",
                                "/api/delivery-service/user/tracking/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-ui/**",
                                "/swagger-ui.html/**", "/api-docs/**",
                                "/api/delivery-service/v3/api-docs",
                                "/api/delivery-service/v3/api-docs/**",
                                "/actuator/**",
                                "/actuator"
                                ).permitAll()
                        .anyRequest()
                        .hasAuthority("SCOPE_delivery.read"))
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthConverter)
                        )
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .exceptionHandling(c -> c.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
