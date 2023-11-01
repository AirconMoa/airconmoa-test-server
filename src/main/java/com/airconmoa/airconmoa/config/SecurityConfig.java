package com.airconmoa.airconmoa.config;

import com.airconmoa.airconmoa.config.jwt.JwtTokenFilter;
import com.airconmoa.airconmoa.domain.Role;
import com.airconmoa.airconmoa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private static String secretKey = "dGhpc2lzYWlyY29ubW9hand0c2VjcmV0a2V5dGhpc2lzYWlyY29ubW9hand0c2VjcmV0a2V5dGhpc2lzYWlyY29ubW9hand0c2VjcmV0a2V5dGhpc2lz";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic(httpBasic -> httpBasic.disable())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(authorizeRequests -> {
                    authorizeRequests
                            .requestMatchers(new AntPathRequestMatcher("/api/user/login")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/api/user/info")).hasAuthority(Role.USER.name())
                            .requestMatchers(new AntPathRequestMatcher("/api/user/company")).hasAuthority(Role.COMPANY.name())
                            .anyRequest().authenticated();
                })
                .build();

    }
}
