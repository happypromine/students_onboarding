package com.web.students_onboarding.config;

import com.web.students_onboarding.model.User;
import com.web.students_onboarding.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/student/**").hasRole("STUDENT")
                        .requestMatchers("/curator/**").hasRole("CURATOR")
                        .requestMatchers("/", "/login", "/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/auth/")
                        .loginProcessingUrl("/auth/")
                        .successHandler((request, response, authentication) -> {
                            String role = authentication.getAuthorities().stream()
                                    .findFirst()
                                    .map(authority -> authority.getAuthority())
                                    .orElse("");

                            if (role.equals("ROLE_ADMIN")) {
                                response.sendRedirect("/admin/dashboard");
                            } else if (role.equals("ROLE_CURATOR")) {
                                response.sendRedirect("/curator/dashboard");
                            } else {
                                response.sendRedirect("/student/dashboard");
                            }
                        })
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/auth/")
                        .permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("Пользователь не найден");
            }
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPasswordHash(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()))
            );
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
