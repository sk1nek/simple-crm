package me.mjaroszewicz.crmapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/style.css", "/static/**", "/perform-login", "/register").permitAll()
                .and()
                .authorizeRequests().antMatchers("/**").fullyAuthenticated()
                .and()
                .formLogin().loginPage("/login").permitAll();
    }
}

