package com.api.note.quiz.api.v1;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.api.note.quiz.config.AppConfig;
import com.api.note.quiz.config.AutoAuthenticationManager;
import com.api.note.quiz.domain.TAccount;
import com.api.note.quiz.form.AccountCreateForm;
import com.api.note.quiz.service.AccountService;
import com.api.note.quiz.service.FacebookService;
import com.restfb.types.User;

/**
 * ログインAPI
 */
@Controller
@RequestMapping("/api/v1/login")
public class LoginController {

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AutoAuthenticationManager autoAuthenticationManager;

	@Autowired
	private FacebookService facebookService;

	/**
	 * Facebookログイン
	 */
	@GetMapping("/facebook")
	public String loginToFacebook() {
		return "redirect:" + facebookService.createFacebookAuthorizationURL();
	}

	/**
	 * Facebookコールバック
	 */
	@GetMapping("/facebook/cbk")
	public String createFacebookAccessToken(@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "error", required = false) String error, HttpServletRequest request) {
		if (error != null) {
			return "redirect:" + appConfig.getUrl();
		}
		// Facebook認証
		User facebookUser = facebookService.getFacebookUser(code);

		// DBに登録されているユーザーを取得
		TAccount account = accountService.findByFacebookId(facebookUser.getThirdPartyId());
		// TODO BANされている場合
		if (account == null) {
			// ユーザー登録
			AccountCreateForm form = new AccountCreateForm();
			form.setLoginId(facebookUser.getThirdPartyId()); // TODO ログインID検討
			form.setName(facebookUser.getName());
			form.setMail(facebookUser.getEmail());
			form.setPassword("FACEBOOK," + facebookUser.getThirdPartyId() + ",USER"); // TODO 仮パスワード長すぎ
			form.setImgUrl(facebookUser.getPicture() == null ? null : facebookUser.getPicture().getUrl());
			form.setFacebook(facebookUser.getThirdPartyId());
			accountService.create(form);
		}
		// ログイン
		autoLogin(request, facebookUser);
		return "redirect:" + appConfig.getUrl();
	}

	/**
	 * 独自ログインする
	 */
	private void autoLogin(HttpServletRequest request, User facebookUser) {
		// ログイン
		String pass = "FACEBOOK," + facebookUser.getThirdPartyId() + ",USER";
		UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
				facebookUser.getThirdPartyId(), pass, AuthorityUtils.createAuthorityList("ROLE_USER"));
		authReq.setDetails(new WebAuthenticationDetails(request));
		Authentication auth = autoAuthenticationManager.authenticate(authReq);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}
}
