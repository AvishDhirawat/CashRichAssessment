package com.cashrich.CashRichAssessment.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        System.out.println("inside user service");
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("Sam")
                .password("Sam@123")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("inside user security");
        http
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/**").hasAnyRole("USER")
                        .anyRequest().authenticated()).httpBasic(withDefaults())
                .formLogin(withDefaults());
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }

//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/api/users/**").permitAll() // Allow access to user-related endpoints without authentication
//                .anyRequest().authenticated() // Require authentication for other endpoints
//                .and()
//                .httpBasic(); // Use basic authentication
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/api/admin/**").hasRole("ADMIN") // Require ADMIN role for admin endpoints
//                .antMatchers("/api/users/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic();
//    }

}
