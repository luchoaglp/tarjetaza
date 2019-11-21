package com.tarjetaza.config;

import com.tarjetaza.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_MATCHERS = {
            "/css/**", "/js/**", "/img/**", "/font/**", "/",
            "/", "/home", "/signin",
            "/api/requests", "/api/cards/**", "/api/credits",
            // "/requests/**", "/api/requests/**", "/users/**" //DEV
            // "/claims/**" //DEV
            // "/reports/requests" //DEV
    };

    private static final String[] ADMIN_MATCHERS = {
            "/requests/**", "/api/requests/**",
            "/duplicate-requests/**",
            "/users/**", "/user/**",
            "/params/**", "/certificates/**",
            "/claims/**",
            "/consumption/**",
            "/reports/**"
    };

    private static final String[] USER_MATCHERS = {
            "/requests/**", "/api/requests/**",
            "/duplicate-requests/**",
            "/user/**",
            "/params/**", "/certificates/**",
            "/claims/**",
            "/consumption/**",
            "/reports/**"
    };

    private static final String[] OPERATOR_MATCHERS = {
            "/claims/**"
    };

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //http.headers().frameOptions().disable(); // Only for H2

        http
                .authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .antMatchers(ADMIN_MATCHERS).access("hasRole('ROLE_OPERATOR') and hasRole('ROLE_USER') and hasRole('ROLE_ADMIN')")
                .antMatchers(USER_MATCHERS).access("hasRole('ROLE_OPERATOR') and hasRole('ROLE_USER')")
                .antMatchers(OPERATOR_MATCHERS).access("hasRole('ROLE_OPERATOR')")
                .anyRequest()
                .authenticated();

        http
                .csrf().disable()
                .cors().disable()
                .formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/signin").permitAll()
                .defaultSuccessUrl("/")
                .failureUrl("/signin?error")
                //.successHandler(customAuthenticationSuccessHandler())
                //.failureHandler(customAuthenticationFailureHandler())
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //.addLogoutHandler(customLogoutHandler())
                .logoutSuccessUrl("/")
                /*
                .deleteCookies("remember-me").permitAll()
                .and()
                .rememberMe()
                */;
    }

    /*
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
    */

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

}
