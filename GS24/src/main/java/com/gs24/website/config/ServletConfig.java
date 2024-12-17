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

// servlet-context.xml과 동일
@Configuration // Spring Container에서 관리하는 설정 클래스
@EnableWebMvc // Spring MVC 기능 사용
@ComponentScan(basePackages = { "com.gs24.website" })
// component scan 설정
public class ServletConfig implements WebMvcConfigurer {
   // ViewResolver 설정 메소드
   @Override
   public void configureViewResolvers(ViewResolverRegistry registry) {
      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
      viewResolver.setPrefix("/WEB-INF/views/");
      viewResolver.setSuffix(".jsp");
      registry.viewResolver(viewResolver);
   }

<<<<<<< Updated upstream
	// ResourceHandlers 설정 메소드
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// resources 디렉토리 설정
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
<<<<<<< Updated upstream
=======
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		
		resolver.setMaxUploadSize(1024 * 1024 * 30);
		
		resolver.setMaxUploadSizePerFile(1024 * 1024 * 10);
		
		return resolver;
	}
	
	@Bean
	public String uploadPath() {
		return "C:\\Users\\sdedu\\Desktop\\gsproject\\GS24\\src\\main\\webapp";
	}
>>>>>>> Stashed changes
=======
   // ResourceHandlers 설정 메소드
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      // resources 디렉토리 설정
      registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
   }

   @Bean
   public CommonsMultipartResolver multipartResolver() {
      CommonsMultipartResolver resolver = new CommonsMultipartResolver();

      resolver.setMaxUploadSize(1024 * 1024 * 30);

      resolver.setMaxUploadSizePerFile(1024 * 1024 * 10);

      return resolver;
   }

   @Bean
   public String uploadPath() {
      return "C:\\Users\\sdedu\\Desktop\\gsproject\\GS24\\src\\main\\webapp";
   }
>>>>>>> Stashed changes
}
