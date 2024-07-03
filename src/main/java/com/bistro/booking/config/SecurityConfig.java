package com.bistro.booking.config;


import com.bistro.booking.exception.AppAccessDeniedHandler;
import com.bistro.booking.exception.JwtAuthenticationEntryPoint;
import com.bistro.booking.filter.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@AllArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuth;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final AppAccessDeniedHandler appAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/swagger-ui/**","/swagger-ui.html","/swagger-resources/**","/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                ).exceptionHandling( configure -> configure.authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(appAccessDeniedHandler))
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuth, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
