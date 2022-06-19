package com.josecarlos.securitymatchermethods.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {

        var manager = new InMemoryUserDetailsManager();

        var john = User.withUsername("john")
                .password("12345")
                .roles("ADMIN")
                .build();

        var jane = User.withUsername("jane")
                .password("12345")
                .roles("MANAGER")
                .build();

        manager.createUser(john);
        manager.createUser(jane);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

//        http.authorizeRequests()
//                .mvcMatchers("/hello").hasRole("ADMIN")
//                .mvcMatchers("/ciao").hasRole("MANAGER")
//                .anyRequest().permitAll();

//        http.authorizeRequests()
//                .mvcMatchers(HttpMethod.GET, "/a").authenticated()
//                .mvcMatchers(HttpMethod.POST, "/a").permitAll()
//                .anyRequest().denyAll();

        http.authorizeRequests()
//                .antMatchers("/hello").authenticated()
//                .mvcMatchers("/a/b/**").authenticated()
//                .mvcMatchers("/product/{code:^[0-9]*$}").permitAll()
                .regexMatchers(".*/(us|uk|ca)+/(en|fr).*").authenticated()
                .anyRequest().hasAuthority("premium");

        http.csrf().disable();
    }
}
