package com.summit.gym.Sumit_Gym_Management_System.Config;

import com.summit.gym.Sumit_Gym_Management_System.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Profile("!default")
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String STATIC_RESOURCES_PATH =
            "http://localhost:63342/Sumit-Gym-Management-System/static/";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


        httpSecurity
//                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
//                        .anyRequest().permitAll());
//                        .requestMatchers("/admin/**").hasAuthority(Role.ROLE_ADMIN.name())
                        .requestMatchers(AntPathRequestMatcher
                                .antMatcher("/**/admin/**")).hasAuthority(Role.ROLE_ADMIN.name())
//                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated())

//                .formLogin(form -> form
//                        .loginPage("http://localhost:63342/Sumit-Gym-Management-System/static/login.html")
//                        .loginProcessingUrl("http://localhost:8080/login")
//                        .permitAll())

                .formLogin(withDefaults())
                .httpBasic(AbstractHttpConfigurer::disable)


//                .httpBasic(AbstractHttpConfigurer::disable)
//                .exceptionHandling(exceptions -> exceptions
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) // Return 401 for unauthenticated access
//                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            response.setStatus(HttpStatus.FORBIDDEN.value());
//                        })
//                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))) // Return 401 for unauthenticated access

                .logout(logout -> logout
                        .logoutSuccessHandler(logoutSuccessHandler()));

//                .formLogin(form -> form
//                        .loginPage("/login3.html") // Set the custom login page URL
//                        .loginProcessingUrl("/login") // URL for form submission
//                        .defaultSuccessUrl("/dashboard", true) // Redirect to dashboard on successful login
//                        .failureUrl("/login3?error=true") // Redirect to login page on failure
//                        .permitAll())

//            .logout(logout -> logout
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout")
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID"));
        return httpSecurity.build();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            // Redirect to custom page after logout
            response.sendRedirect(STATIC_RESOURCES_PATH + "login.html");  // Adjust as needed
        };
    }

    @Bean
    public PasswordEncoder passwordEncode() {
        return new BCryptPasswordEncoder();
    }


}
