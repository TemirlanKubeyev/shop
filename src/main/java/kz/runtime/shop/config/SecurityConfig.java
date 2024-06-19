package kz.runtime.shop.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(authorizationConfigurer -> {
            authorizationConfigurer.requestMatchers("/categories").authenticated();
            authorizationConfigurer.requestMatchers("/categories").hasRole("admin");
            authorizationConfigurer.requestMatchers("/add_products").authenticated();
            authorizationConfigurer.requestMatchers("/categories").hasRole("admin");
            authorizationConfigurer.requestMatchers("/basket").authenticated();
            authorizationConfigurer.requestMatchers("/orders").authenticated();
            authorizationConfigurer.requestMatchers("/admin/reviews").authenticated();
            authorizationConfigurer.requestMatchers("/categories").hasRole("admin");
            authorizationConfigurer.requestMatchers("/admin/orders").authenticated();
            authorizationConfigurer.requestMatchers("/categories").hasRole("admin");
            authorizationConfigurer.requestMatchers("/admin/homePage").authenticated();
            authorizationConfigurer.requestMatchers("/categories").hasRole("admin");
            authorizationConfigurer.anyRequest().permitAll();
        });
        httpSecurity.formLogin(formLoginConfigurer -> {
            formLoginConfigurer.defaultSuccessUrl("/products");
        });
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}


