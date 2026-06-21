package com.yobrunox.tp01backendhwt.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtMiddlewareFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .cors(Customizer.withDefaults())
                .addFilterBefore(jwtMiddlewareFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authRequest ->
                                authRequest
                                        //.requestMatchers("/auth/**").hasAnyRole("ADMIN")
                                        .requestMatchers("/auth/**").permitAll()
                                        //.requestMatchers("/auth/register").permitAll()
                                        //.requestMatchers("documentation/**").permitAll()
                                        //.requestMatchers("swagger-ui/**").permitAll()
                                        //.requestMatchers("admin/**").hasAnyRole("ADMIN")
                                        //.requestMatchers("user/**").hasAnyRole("USER")
                                        .anyRequest().authenticated()
                )
//                .exceptionHandling(
//                        e->e.authenticationEntryPoint(controllerAdvice)
//                )

                .sessionManagement(
                        sessionManager-> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .csrf(csrf->csrf.disable())
                .build();
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        //.allowedOrigins("http://127.0.0.7:5500")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

}