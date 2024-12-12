package com.gs24.website.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

// root-context.xml과 동일
@Configuration
@ComponentScan(basePackages = { "com.gs24.website.service" }) // 패키지 경로로 Component 스캐닝
@MapperScan(basePackages = { "com.gs24.website.persistence" }) // 패키지 경로로 Mapper 스캐닝
public class RootConfig {

	@Bean // 스프링 bean으로 설정. xml의 <bean>태그와 동일
	public DataSource dataScource() { // DataSource 객체 리턴 메소드
		HikariConfig config = new HikariConfig(); // HikariConfig : DBCP 라이브러리
		config.setDriverClassName("oracle.jdbc.OracleDriver");
		config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
		config.setUsername("STUDY");
		config.setPassword("1234");

		config.setMaximumPoolSize(10); // 최대 풀(Pool) 크기 설정
		config.setConnectionTimeout(30000); // Connection 타임 아웃 설정(30초)
		HikariDataSource ds = new HikariDataSource(config);

		return ds;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataScource());
		return (SqlSessionFactory) sqlSessionFactoryBean.getObject();
	}

} // end RootConfig
