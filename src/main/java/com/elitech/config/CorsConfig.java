package com.elitech.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

	public WebMvcConfigurer corsConfigurer()
	{
		return new WebMvcConfigurer() {
			@SuppressWarnings("unused")
			public void addCorsMapping(CorsRegistry registry)
				{
					registry.addMapping("/**")
					.allowedOrigins("http://localhost:4200")
					.allowedMethods("GET","POST","PUT","DELETE","PATCH")
					.allowedHeaders("*")
					.allowCredentials(true);
				}
			};
		}
	}
	
	

