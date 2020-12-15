package com.hti.pos.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by ravuthz
 * Date : 12/2/2020, 10:19 PM
 * Email : ravuthz@gmail.com, yovannaravuth@gmail.com
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //
////    @Autowired
////    private UserDetailsService userDetailsService;
//
////    @Autowired
////    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
////    @Autowired
////    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
////    }
//
//
////    @Bean
////    @Override
////    public AuthenticationManager authenticationManagerBean() throws Exception {
////        return super.authenticationManagerBean();
////    }
//
//
////    @Bean
////    public BCryptPasswordEncoder bCryptPasswordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .anyRequest().permitAll()
////                .antMatchers("/oauth/**").permitAll()
////                .antMatchers("/manages/**").permitAll()
////                .anyRequest().authenticated()
//                .and().httpBasic()
//                .and().csrf().disable();
//    }
//
    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    //
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .permitAll();
//                .antMatchers("/**/*").permitAll();
//                .antMatchers(AUTH_WHITELIST).permitAll()
//                .antMatchers("/**/*").denyAll();

    }

    //
//
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .anyRequest();
//                .antMatchers(AUTH_WHITELIST);
//                .antMatchers(
//                        "/manages/**",
//                        "/webjars/**",
//                        "/v2/api-docs",
//                        "/swagger-resources/**",
//                        "/swagger-ui.html**");
    }

}
