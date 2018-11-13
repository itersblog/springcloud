package com.itersblog.springcloud.order.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.itersblog.springcloud.order.interceptor.GlobalRequestIdInterceptor;

@Configuration
public class InterceptorConfigurer extends WebMvcConfigurerAdapter {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new GlobalRequestIdInterceptor());
	}
}
