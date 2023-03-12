package com.biletimcepte.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/register/**").permitAll()
                        .antMatchers("/", "/home").permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .antMatchers(HttpMethod.POST, "/voyages/**").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/voyages/**").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/voyages/**").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.GET, "/voyages").hasAnyAuthority("ADMIN", "USER")
                        .antMatchers(HttpMethod.GET, "/voyages/totalprice/**").hasAnyAuthority("ADMIN")
                        .antMatchers(HttpMethod.GET, "/voyages/totalsoldtickets/**").hasAnyAuthority("ADMIN")
                        .antMatchers("/tickets/**").hasAnyAuthority("ADMIN")
                        .antMatchers("/tickets/user/**").hasAnyAuthority("ADMIN", "USER")
                        .antMatchers(HttpMethod.GET, "/bookings/**").hasAnyAuthority("ADMIN")
                        .antMatchers(HttpMethod.POST, "/bookings/**").hasAnyAuthority("ADMIN", "USER")
                        .antMatchers(HttpMethod.PUT, "/bookings/**").hasAnyAuthority("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/bookings/**").hasAnyAuthority("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/users").hasAnyAuthority("ADMIN", "USER")
                        .antMatchers(HttpMethod.DELETE, "/users/**").hasAnyAuthority("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/users/**").hasAnyAuthority("ADMIN", "USER")
                        .antMatchers(HttpMethod.GET, "/users/**").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll
                ).logout(LogoutConfigurer::permitAll);
    }
}