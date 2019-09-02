package com.springinaction.security;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;



@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DruidDataSource druidDataSource;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(druidDataSource).usersByUsernameQuery("" +
                "select username, password, true from spittr where username=?").authoritiesByUsernameQuery("" +
                "select username, 'ROLE_USER' from spittr where username=?");

//        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER").and()
//                .withUser("admin").password("password").roles("USER","ADMIN");
    }


    //配置哪些请求需要安全控制，哪些不需要
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().formLogin().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/spittles/register").hasRole("USER")
                .and()
                .httpBasic()
                .realmName("Spittr")
        .and()
        .rememberMe()
        .tokenValiditySeconds(2419200)
        .key("spittrKey")
        .and()
        .logout()
        .logoutSuccessUrl("/spittles/home")
        .logoutUrl("/spittles/signout");
//                .requiresChannel()
//                .antMatchers("/spittles/list").requiresSecure();
    }
}
