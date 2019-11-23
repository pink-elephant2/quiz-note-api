package com.api.note.quiz.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.MappedInterceptor;

import com.api.note.quiz.aop.CommonHandlerInterceptor;

/**
 * 共通設定
 */
@Configuration
public class Config {

	/**
	 * Dozer設定
	 */
	@Bean
	DozerBeanMapper getDozerBeanMapperFactoryBean() {
		return new DozerBeanMapper();
	}

	/**
	 * 共通ハンドラ
	 */
	@Bean
	public CommonHandlerInterceptor commonHandlerInterceptor() {
		return new CommonHandlerInterceptor();
	}

	/**
	 * 共通ハンドラインターセプタ
	 */
	@Bean
	public MappedInterceptor interceptor() {
		return new MappedInterceptor(new String[] { "/**" }, commonHandlerInterceptor());
	}
}
