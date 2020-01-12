package com.api.note.quiz.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.openid.OpenIDAuthenticationProvider;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.api.note.quiz.service.impl.UserDetailsServiceImpl;

/**
 * SpringSecurity設定
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	/**
	 * 認証設定
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 認可の設定
		http.authorizeRequests()
				// 認証ページ
				.antMatchers("/api/v1/user/**").hasRole("USER").and()
				// ログイン設定
				.formLogin().loginPage("/").loginProcessingUrl("/api/v1/login").usernameParameter("loginId")
				.passwordParameter("password").successHandler(new AppAuthenticationSuccessHandler())
				.failureHandler(new AppAuthenticationFailureHandler()).and()
				// 認証認可失敗
				.exceptionHandling().authenticationEntryPoint(new AppAuthenticationEntryPoint())
				.accessDeniedHandler(new AppAccessDeniedHandler()).and()
				// ログアウト設定
				.logout().logoutUrl("/api/v1/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID")
				.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()).and()
				// CSRF
				.csrf().disable() // TODO CSRF有効にする
				// CORS
				.cors().configurationSource(corsConfigurationSource());
	}

	/**
	 * CORS設定
	 *
	 * @return CORS設定
	 */
	private CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name(),
				HttpMethod.PUT.name(), HttpMethod.DELETE.name()));
		corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
		corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

		return corsConfigurationSource;
	}

	/**
	 * Facebookプロバイダ
	 */
	@Bean(name = "openIDAuthenticationProvider")
	public AuthenticationProvider openIDAuthenticationProvider() {
		OpenIDAuthenticationProvider openIDAuthenticationProvider = new OpenIDAuthenticationProvider();
		openIDAuthenticationProvider.setUserDetailsService(userDetailsService);
		return openIDAuthenticationProvider;
	}

	/**
	 * 独自ログイン
	 */
	@Bean
	public AutoAuthenticationManager autoAuthenticationManager() {
		return new AutoAuthenticationManager();
	}

	/**
	 * パスワードハッシュ化方式
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 認証処理設定
	 * <ul>
	 * <li>入力されたIDに紐づくユーザー検索の実装</li>
	 * <li>パスワードハッシュ化方式</li>
	 * </ul>
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.eraseCredentials(true).userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	/**
	 * ログイン成功
	 */
	public class AppAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication auth) throws IOException, ServletException {
			if (response.isCommitted()) {
				return;
			}

			response.setStatus(HttpStatus.OK.value());
			clearAuthenticationAttributes(request);
		}

		/**
		 * Removes temporary authentication-related data which may have been
		 * stored in the session during the authentication process.
		 */
		private void clearAuthenticationAttributes(HttpServletRequest request) {
			HttpSession session = request.getSession(false);

			if (session == null) {
				return;
			}
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}

	/**
	 * ログイン失敗
	 */
	public class AppAuthenticationFailureHandler implements AuthenticationFailureHandler {

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
		}
	}

	/**
	 * 未認証のユーザーが認証の必要なAPIにアクセスしたときの処理
	 */
	private class AppAuthenticationEntryPoint implements AuthenticationEntryPoint {

		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException authException) throws IOException, ServletException {
			if (response.isCommitted()) {
				return;
			}
			response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
		}
	}

	/**
	 * ユーザーは認証済みだが未認可のリソースへアクセスしたときの処理
	 */
	private class AppAccessDeniedHandler implements AccessDeniedHandler {

		@Override
		public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
				throws IOException, ServletException {
			response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
		}

	}
}
