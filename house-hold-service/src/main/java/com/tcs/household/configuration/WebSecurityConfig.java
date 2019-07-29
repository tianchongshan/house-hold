package com.tcs.household.configuration;

import com.tcs.household.mgr.security.MyAuthenticationEntryPoint;
import com.tcs.household.mgr.security.MyAuthenticationProvider;
import com.tcs.household.mgr.security.filter.MyAuthenticationTokenFilter;
import com.tcs.household.mgr.security.handler.MyAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * web安全配置
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private MyAuthenticationProvider authenticationProvider;

    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;

    @Value("${admin.login.url}")
    private String loginUrl;

    @Bean
    public MyAuthenticationTokenFilter authenticationTokenFilterBean()throws Exception {
        return new MyAuthenticationTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()   //使用jwt，不使用csrf
        .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler)
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()  //基于token，不使用session
        .authorizeRequests()          //请求授权
        .antMatchers(                 //允许对于网站静态资源的无授权访问
                HttpMethod.GET,
                "/",
                "/h5/**",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.vue",
                "/**/*.css",
                "/**/*.js",
                "/**/*.js.map",
                "/**/*.svg",
                "/**/fonts/*.*"
        ).permitAll()
                .antMatchers(loginUrl).permitAll()         //授权api
                .antMatchers("/api/web/**").permitAll()
                .antMatchers("/sys/user/add").permitAll()
                .anyRequest().authenticated();      //禁用其他链接(需要认证)
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);  //添加JWT filter

        http.headers().cacheControl();  //禁用缓存
    }















}
