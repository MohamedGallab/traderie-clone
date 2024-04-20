package com.Traderie_User.User_Service.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig{



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
               .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize-> authorize.
                        requestMatchers("/**")
                        .permitAll())

                .formLogin(AbstractHttpConfigurer::disable)// Disable form login
                .httpBasic(AbstractHttpConfigurer::disable); // Disable HTTP Basic authentication

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // You can adjust the strength of the BCrypt hashing algorithm if needed
        return new BCryptPasswordEncoder();
    }

}
