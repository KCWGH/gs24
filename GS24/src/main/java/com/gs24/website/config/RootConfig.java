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

// root-context.xml怨� �룞�씪
@Configuration
@ComponentScan(basePackages = { "com.gs24.website.service" })
@MapperScan(basePackages = { "com.gs24.website.persistence" })
public class RootConfig {

	@Bean
	public DataSource dataScource() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName("oracle.jdbc.OracleDriver");
		config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
		config.setUsername("SPRINGPROJECT");
		config.setPassword("1234");

		config.setMaximumPoolSize(10);
		config.setConnectionTimeout(30000);
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
