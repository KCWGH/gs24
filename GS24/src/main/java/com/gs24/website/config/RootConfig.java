package com.gs24.website.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

// root-context.xml과 동일
@Configuration
@EnableScheduling
@ComponentScan(basePackages = "com.gs24.website.service")
@MapperScan(basePackages = { "com.gs24.website.persistence" }) // 패키지 경로로 Mapper 스캐닝
@EnableTransactionManagement
public class RootConfig {

	@Bean // 스프링 bean으로 설정. xml의 <bean>태그와 동일
	public DataSource dataScource() { // DataSource 객체 리턴 메소드
		HikariConfig config = new HikariConfig(); // HikariConfig : DBCP 라이브러리
		config.setDriverClassName("oracle.jdbc.OracleDriver");
		config.setJdbcUrl("jdbc:oracle:thin:@192.168.0.136:1521:xe");
		config.setUsername("STUDY");
		config.setPassword("1234");

		config.setMaximumPoolSize(10); // 최대 풀(Pool) 크기 설정
		config.setConnectionTimeout(30000); // Connection 타임 아웃 설정(30초)
		HikariDataSource ds = new HikariDataSource(config);

		return ds;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataScource()); // 기존의 DataSource를 사용
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataScource());
		return (SqlSessionFactory) sqlSessionFactoryBean.getObject();
	}

	@Bean
	public TransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataScource());
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public RecaptchaConfig recaptchaConfig() {
		return new RecaptchaConfig();
	}

} // end RootConfig
