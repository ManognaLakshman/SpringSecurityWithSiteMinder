package com.ndg.springdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import com.ndg.springdemo.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;  
    private PreAuthenticatedAuthenticationProvider preAuthenticatedProvider;

    public WebSecurityConfiguration() {
        super();

        userDetailsService = new CustomUserDetailsService();
        UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = new UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken>(
                userDetailsService);

        preAuthenticatedProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedProvider.setPreAuthenticatedUserDetailsService(wrapper);
    }


    @Override
    protected void configure(
            AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {


        // @formatter:off
        authenticationManagerBuilder //
            .authenticationProvider(preAuthenticatedProvider);
        // @formatter:on
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // @formatter:off

        RequestHeaderAuthenticationFilter siteMinderFilter = new RequestHeaderAuthenticationFilter();
        siteMinderFilter.setAuthenticationManager(authenticationManager());

        httpSecurity
        	.cors()
        .and()
            .addFilter(siteMinderFilter)
            .authorizeRequests()
                .antMatchers("/login","/login.request","/logout").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().hasAuthority("LEADERSHIP")
        .and()
            .formLogin()
                .loginPage("/login.request")
                .loginProcessingUrl("/login")
                .failureUrl("/login.request?error")
                .permitAll()
        .and()
            .logout()
                .logoutUrl("/logout")
                .permitAll()
                .logoutSuccessUrl("/login.request")
        ;

        // @formatter:on
    }
}
