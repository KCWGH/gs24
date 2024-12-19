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
@ComponentScan(basePackages = { "com.gs24.website" })

public class ServletConfig implements WebMvcConfigurer {
<<<<<<< HEAD
<<<<<<< HEAD

   @Override
   public void configureViewResolvers(ViewResolverRegistry registry) {
      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
      viewResolver.setPrefix("/WEB-INF/views/");
      viewResolver.setSuffix(".jsp");
      registry.viewResolver(viewResolver);
   }

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {

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
=======
	// ViewResolver 설정 메소드
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		registry.viewResolver(viewResolver);
	}

=======
	// ViewResolver 설정 메소드
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		registry.viewResolver(viewResolver);
	}

>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
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
<<<<<<< HEAD
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
=======
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
}
