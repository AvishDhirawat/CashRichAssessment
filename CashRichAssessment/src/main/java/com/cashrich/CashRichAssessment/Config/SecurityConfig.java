package com.cashrich.CashRichAssessment.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

//
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //    @Bean
//    public UserDetailsService userDetailsService() {
//        System.out.println("inside user service");
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("Sam")
//                .password("Sam@123")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        System.out.println("inside user security");
//        http
//                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/**").hasAnyRole("USER")
//                        .anyRequest().authenticated()).httpBasic(withDefaults())
//                .formLogin(withDefaults());
//        http.csrf(csrf -> csrf.disable());
//        return http.build();
//    }
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
////    @Bean
////    public UserDetailsService userDetailsService() {
////        System.out.println("inside user service");
////        UserDetails user = User.withDefaultPasswordEncoder()
////                .username("Sam")
////                .password("Sam@123")
////                .roles("USER")
////                .build();
////        return new InMemoryUserDetailsManager(user);
////    }
//
    @Autowired
    DataSource dataSource;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(encoder)
                .usersByUsernameQuery("select username, password, 'true' as enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, role from users where username=?");

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("inside user security");
        http
                .authorizeHttpRequests((authorize) -> authorize//.requestMatchers("/**").hasAnyRole("USER")
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()).httpBasic(withDefaults())
                .formLogin(withDefaults());
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }

}
