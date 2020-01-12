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
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.note.quiz.aop.SessionInfoContextHolder;
import com.api.note.quiz.config.AppConfig;
import com.api.note.quiz.config.AutoAuthenticationManager;
import com.api.note.quiz.domain.TAccount;
import com.api.note.quiz.enums.UserType;
import com.api.note.quiz.exception.NotFoundException;
import com.api.note.quiz.form.AccountCreateForm;
import com.api.note.quiz.form.RegisterUserForm;
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
	 * ログインチェック
	 * @return ログインID
	 */
	@GetMapping("/check")
	@ResponseBody
	public String check() {
		if (!SessionInfoContextHolder.isAuthenticated()) {
			throw new NotFoundException("未ログイン");
		}
		return "\"" + SessionInfoContextHolder.getSessionInfo().getLoginId() + "\"";
	}

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
		RegisterUserForm registerUser = convertRegisterUserFormByFacebookCode(code);

		// DBに登録されているユーザーを取得
		TAccount account = accountService.findByFacebookId(registerUser.getThirdPartyId());
		// TODO BANされている場合
		if (account == null) {
			// ユーザー登録
			AccountCreateForm form = new AccountCreateForm();
			form.setLoginId(registerUser.getThirdPartyId()); // TODO ログインID検討
			form.setMail(registerUser.getUserMail());
			form.setPassword(registerUser.getUserType().name() + "," + registerUser.getThirdPartyId() + ",USER"); // TODO 仮パスワード長すぎ
			form.setFacebook(registerUser.getThirdPartyId());
			accountService.create(form);
		}
		// ログイン
		autoLogin(request, registerUser, account);
		return "redirect:" + appConfig.getUrl();
	}

	private RegisterUserForm convertRegisterUserFormByFacebookCode(String code) {
		User user = facebookService.getFacebookUser(code);
		RegisterUserForm registerUser = new RegisterUserForm();
		registerUser.setThirdPartyId(user.getId());
		registerUser.setUserType(UserType.FACEBOOK);
		registerUser.setUserMail(user.getEmail());
		registerUser.setUserName(user.getName());
		registerUser.setUserImgUrl(user.getPicture() == null ? null : user.getPicture().getUrl());
		return registerUser;
	}

	/**
	 * 独自ログインする
	 */
	private void autoLogin(HttpServletRequest request, RegisterUserForm registerUser, TAccount account) {
		// ログイン
		String pass = account.getLoginId() + "," + account.getFacebook() + ",USER";
		UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
				registerUser.getThirdPartyId(), pass, AuthorityUtils.createAuthorityList("ROLE_USER"));
		authReq.setDetails(new WebAuthenticationDetails(request));
		Authentication auth = autoAuthenticationManager.authenticate(authReq);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

}
