package com.gs24.website.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.gs24.website.service.CustomLoginSuccessHandler;
import com.gs24.website.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    @Bean
    public RequestCache requestCache() {
        return new HttpSessionRequestCache(); // 로그인 전 요청 URL 저장 기능
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
            .antMatchers("/auth/**").permitAll()
            .antMatchers("/convenience/**").permitAll()
            .antMatchers("/user/mypage", "/user/verify", "/user/change-pw").authenticated()
            .antMatchers("/user/**").permitAll()
            .antMatchers("/member/myhistory").access("hasRole('ROLE_MEMBER')")
            .antMatchers("/member/register").permitAll()
            .antMatchers("/owner/register").permitAll()
            .antMatchers("/giftcard/list", "/giftcard/detail", "/giftcard/purchase", "/giftcard/grant").access("hasRole('ROLE_MEMBER')")
            .antMatchers("/coupon/**").access("hasRole('ROLE_ADMIN')")
            .antMatchers("/food/detail", "/food/list").permitAll()
            .antMatchers("/food/register", "/food/update").access("hasRole('ROLE_OWNER')")
            .antMatchers("/imgfood/register").authenticated()
            .antMatchers("/notice/list").permitAll()
            .antMatchers("/notice/detail").permitAll()
            .antMatchers("/notice/modify").access("hasRole('ROLE_ADMIN')")
            .antMatchers("/notice/register").access("hasRole('ROLE_ADMIN')")
            .antMatchers("/preorder/create").access("hasRole('ROLE_MEMBER')")
            .antMatchers("/preorder/update").access("hasRole('ROLE_OWNER')")
            .antMatchers("/preorder/list").access("hasRole('ROLE_MEMBER')")
            .antMatchers("/preorder/all/**").access("hasRole('ROLE_MEMBER')")
            .antMatchers("/preorder/cancel").access("hasRole('ROLE_MEMBER')")
            .antMatchers("/preorder/delete").access("hasRole('ROLE_MEMBER')")
            .antMatchers("/preorder/check").access("hasRole('ROLE_OWNER')")
            .antMatchers("/preorder/pickedup").access("hasRole('ROLE_MEMBER')")
            .antMatchers("/review/list").permitAll()
            .antMatchers("/review/register").access("hasRole('ROLE_MEMBER')")
            .antMatchers("/review/update").access("hasRole('ROLE_MEMBER')")
            .antMatchers("/question/list").authenticated()
            .antMatchers("/question/modify").access("hasRole('ROLE_MEMBER')")
            .antMatchers("/question/ownerList").access("hasRole('ROLE_OWNER')")
            .antMatchers("/question/detail").permitAll()
            .antMatchers("/question/register").access("hasRole('ROLE_MEMBER')")
            .antMatchers("/foodlist/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')");

        httpSecurity.exceptionHandling().accessDeniedPage("/auth/accessDenied");

        httpSecurity.formLogin()
            .loginPage("/auth/login")
            .loginProcessingUrl("/auth/login")
            .successHandler(customLoginSuccessHandler)
            .permitAll();

        httpSecurity.logout()
            .logoutUrl("/auth/logout")
            .logoutSuccessUrl("/convenience/list")
            .invalidateHttpSession(true)
            .clearAuthentication(true);

        // 로그인 전 요청한 URL 저장 기능 추가
        httpSecurity.requestCache().requestCache(requestCache());

        // UTF-8 인코딩 필터 추가
        httpSecurity.addFilterBefore(encodingFilter(), CsrfFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public CharacterEncodingFilter encodingFilter() {
        return new CharacterEncodingFilter("UTF-8");
    }
}
