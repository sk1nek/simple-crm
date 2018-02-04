package me.mjaroszewicz.crmapp.config;

import me.mjaroszewicz.crmapp.repositories.SecurityAnalyzerSecurityContextRepository;
import me.mjaroszewicz.crmapp.validators.SecurityAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityAnalyzer securityAnalyzer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().accessDeniedPage("/denied")
                .and()
                .securityContext().securityContextRepository(new SecurityAnalyzerSecurityContextRepository(securityAnalyzer))
                .and()
                .authorizeRequests().antMatchers("/fa-all.js", "/bulma.min.css", "/style.css", "/static/**", "/perform-login", "/register").permitAll()
                .and()
                .authorizeRequests().antMatchers("/clients/**", "/orders/**", "/complaints/**", "/files/**", "/dashboard", "/account", "/user/**", "/finance/**").fullyAuthenticated()
                .and()
                .authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .csrf()
                .disable()
                .formLogin().loginPage("/login").permitAll();

    }
}

