package com.tom.aws.awstest.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

public class SecurityConfig {
	
	@Value("${application.security.user}")
	private String user;
	
	@Value("${application.security.password}")
	private String password;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
            					.requestMatchers("/api/v1").permitAll()
                                .requestMatchers("/actuator/**").authenticated()
                                .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults()) 
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
	
    @Bean
    UserDetailsService userDetailsService() {
        UserDetails username = User.builder()
            .username(user)
            .password(passwordEncoder().encode(password))
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(username);
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
