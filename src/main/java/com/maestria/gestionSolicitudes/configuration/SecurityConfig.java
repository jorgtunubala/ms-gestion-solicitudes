package com.maestria.gestionSolicitudes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilita CSRF
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configurar CORS
            .authorizeRequests(auth -> auth
                .antMatchers("/msmaestriac/**").permitAll() // Endpoint público
                .anyRequest().permitAll())
                .addFilterBefore(securityHeadersFilter(), HeaderWriterFilter.class); // Filtro para cabeceras de seguridad

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
            "http://localhost:4200", // Origen local para desarrollo
            "https://apptest.unicauca.edu.co:4413")); // Origen del front desplegado
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public HeaderWriterFilter securityHeadersFilter() {
        // Agrega encabezados de seguridad personalizados
        return new HeaderWriterFilter(List.of(
            new StaticHeadersWriter("Cross-Origin-Opener-Policy", "same-origin-allow-popups")
        ));
    }
}
