package com.gs24.website.config;

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
import org.springframework.web.filter.CharacterEncodingFilter;

import com.gs24.website.service.CustomUserDetailsService;

// Spring Security의 설정을 정의하는 클래스
// WebSecurityConfigurerAdapter를 상속하여 보안 기능을 구성
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
// @EnableGlobalMethodSecurity : 인증 및 접근 제어 어노테이션
// prePostEnabled = true : 
// 		@PreAuthorize 및 @PostAuthorize와 같은 표현식을 메서드 수준에서 사용할 수 있게 설정
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// 비밀번호 암호화를 위한 BCryptPasswordEncoder를 빈으로 생성
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// HttpSecurity 객체를 통해 HTTP 보안 기능을 구성
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests() // ROLE_MEMBER와 ROLE_OWNER중에 선택
		.antMatchers("/auth/**").permitAll()
        .antMatchers("/member/mypage").authenticated()
        .antMatchers("/member/myhistory").access("hasRole('ROLE_MEMBER')")
        .antMatchers("/giftcard/list").access("hasRole('ROLE_MEMBER')")
        .antMatchers("/giftcard/detail").access("hasRole('ROLE_MEMBER')")
        .antMatchers("/giftcard/grant").access("hasRole('ROLE_OWNER')")
        .antMatchers("/giftcard/purchase").access("hasRole('ROLE_MEMBER')")
        .antMatchers("/coupon/**").access("hasRole('ROLE_OWNER')")
        .antMatchers("/food/detail").permitAll()
        .antMatchers("/food/list").permitAll()
        .antMatchers("/food/register").access("hasRole('ROLE_OWNER')")
        .antMatchers("/food/update").access("hasRole('ROLE_OWNER')")
        .antMatchers("/imgfood/register").authenticated()
        .antMatchers("/notice/list").permitAll()
        .antMatchers("/notice/modify").access("hasRole('ROLE_OWNER')")
        .antMatchers("/notice/register").access("hasRole('ROLE_OWNER')")
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
        .antMatchers("/question/myList").authenticated()
        .antMatchers("/question/detail").permitAll()
        .antMatchers("/question/register").access("hasRole('ROLE_MEMBER')");

		// 접근 제한 경로 설정
		httpSecurity.exceptionHandling().accessDeniedPage("/auth/accessDenied");

		httpSecurity.formLogin()
        .loginPage("/auth/login")
        .loginProcessingUrl("/auth/login")
        .defaultSuccessUrl("/food/list", false)// 로그인 성공 후 핸들러 설정
        .permitAll();

		httpSecurity.logout().logoutUrl("/auth/logout") // logout url 설정
		.logoutSuccessUrl("/current") // 로그아웃 성공 시 이동할 url 설정
		.invalidateHttpSession(true)
		.clearAuthentication(true);; // 세션 무효화 설정

		// header 정보에 xssProtection 기능 설정
//		httpSecurity.headers().xssProtection().block(true);
//		httpSecurity.headers().contentSecurityPolicy("script-src 'self' https://code.jquery.com 'unsafe-inline' 'unsafe-eval'");

		// encoding 필터를 Csrf 필터보다 먼저 실행
		httpSecurity.addFilterBefore(encodingFilter(), CsrfFilter.class);

	}

	// AuthenticationManagerBuilder 객체를 통해 인증 기능을 구성
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()); // CustomUserDetailsService 빈 적용
	}

	// 사용자 정의 로그인 클래스인 CustomUserDetailsService를 빈으로 생성
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	// CharacterEncodingFilter 빈 생성
	@Bean
	public CharacterEncodingFilter encodingFilter() {
		return new CharacterEncodingFilter("UTF-8");
	}

}