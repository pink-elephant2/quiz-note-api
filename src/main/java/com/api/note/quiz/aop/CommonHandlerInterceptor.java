package com.api.note.quiz.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.api.note.quiz.resources.SessionInfoResource;
import com.api.note.quiz.service.impl.UserDetailsServiceImpl;

/**
 * 共通ハンドラ
 */
public class CommonHandlerInterceptor implements HandlerInterceptor {

	/** 認証サービス. */
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		if (handler == null) {
			return true;
		}
		if (handler instanceof HandlerMethod) {
			// セッションチェック
			SessionInfoResource session = userDetailsService.authenticated();
			if (session != null) {
				// セッションセット
				SessionInfoContextHolder.setSessionInfo(session);
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
