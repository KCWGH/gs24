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
        .antMatchers(
            "/auth/**", "/user/register", "/member/register", "/owner/register",
            "user/find-id", "user/find-pw",
            "/convenience/list", "/convenienceFood/list", "/convenienceFood/detail", "/review/list",
            "/notice/list", "/notice/detail"
        ).permitAll()
        
        .antMatchers(
            "/user/mypage", "/user/verify", "/user/change-pw", 
            "/imgfood/register"
        ).authenticated()
        
        .antMatchers(
            "/member/myhistory", "/giftcard/list", "/giftcard/detail", 
            "/giftcard/purchase", "/giftcard/grant", "/preorder/create", 
            "/preorder/list", "/preorder/all/**", "/preorder/pickedup", 
            "/preorder/cancel", "/preorder/delete", "/review/register", 
            "/review/update", "/question/modify", "/question/register", "/question/list"
        ).access("hasRole('ROLE_MEMBER')")
        
        .antMatchers(
        	"/preorder/update", 
        	"/preorder/check", "/question/ownerList", "/orders/ownerList"
        ).access("hasRole('ROLE_OWNER')")
        
        .antMatchers(
        	"/admin/console", 
        	"/notice/register", "/notice/modify", "/notice/delete", 
            "/coupon/**",
            "/orders/list", 
            "/admin/activate", "/food/register", "/food/update", "/food/checkdelete"
        ).access("hasRole('ROLE_ADMIN')")
    	
    	.antMatchers(
    		"/food/list"
        ).access("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    	
    	.antMatchers(
        	"/member/activate"
        ).access("hasRole('ROLE_DEACTIVATED_MEMBER')")
    	
    	.antMatchers(
        	"/owner/activate", "/owner/request-activation"
        ).access("hasRole('ROLE_DEACTIVATED_OWNER')")
    	
    	.antMatchers(
        	"/user/reactivate"
        ).access("hasRole('ROLE_DEACTIVATED_OWNER') or hasRole('ROLE_DEACTIVATED_MEMBER')");


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
            .clearAuthentication(true)
            .deleteCookies("JSESSIONID");
        
        httpSecurity.sessionManagement().maximumSessions(1).expiredUrl("/auth/login?expired").maxSessionsPreventsLogin(false);

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
