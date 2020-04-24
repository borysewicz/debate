package com.example.Debate.config;

import com.example.Debate.jwt.AuthenticationFilter;
import com.example.Debate.jwt.JwtAuthenticationEntryPoint;
import com.example.Debate.jwt.TokenProvider;
import com.example.Debate.service.UserPrincipalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserPrincipalService userPrincipalService;
    private TokenProvider tokenProvider;
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    public WebSecurityConfig(UserPrincipalService userPrincipalService, TokenProvider tokenProvider, JwtAuthenticationEntryPoint authenticationEntryPoint) {
        this.userPrincipalService = userPrincipalService;
        this.tokenProvider = tokenProvider;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFilter getAuthenticationFilter() {
        return new AuthenticationFilter(tokenProvider, userPrincipalService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userPrincipalService).passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/userLogin/logIn")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/debate/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/debate")
                .permitAll()
                .antMatchers("/user/add")
                .permitAll()
                .antMatchers(HttpMethod.GET,"/argument")
                .permitAll()
                .antMatchers(HttpMethod.GET,"/argument/**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }
}
