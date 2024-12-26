package com.gs24.website.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.gs24.website" }) // 패키지 스캔 설정
public class ServletConfig implements WebMvcConfigurer {
<<<<<<< Updated upstream
=======

   // JSP 뷰 리졸버 설정
>>>>>>> Stashed changes
   @Override
   public void configureViewResolvers(ViewResolverRegistry registry) {
      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
      viewResolver.setPrefix("/WEB-INF/views/"); // 뷰 위치 설정
      viewResolver.setSuffix(".jsp"); // 파일 확장자 설정
      registry.viewResolver(viewResolver); // 뷰 리졸버 등록
   }

   // 정적 리소스(예: CSS, JS) 경로 설정
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/resources/**").addResourceLocations("/resources/"); // 리소스 위치 설정
   }

   // 파일 업로드 처리
   @Bean
   public CommonsMultipartResolver multipartResolver() {
      CommonsMultipartResolver resolver = new CommonsMultipartResolver();
      resolver.setMaxUploadSize(1024 * 1024 * 30); // 최대 업로드 크기 설정 (30MB)
      resolver.setMaxUploadSizePerFile(1024 * 1024 * 10); // 파일당 최대 업로드 크기 (10MB)
      return resolver;
   }

<<<<<<< Updated upstream
=======
   // 파일 업로드 경로 설정
   @Bean
   public String uploadPath() {
      return "C:\\Users\\sdedu\\Desktop\\gsproject\\GS24\\src\\main\\webapp"; // 파일 업로드 경로
   }
>>>>>>> Stashed changes
}
