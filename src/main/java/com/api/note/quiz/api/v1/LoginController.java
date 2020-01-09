package com.api.note.quiz.api.v1;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.api.note.quiz.config.AppConfig;
import com.api.note.quiz.domain.TAccount;
import com.api.note.quiz.enums.UserType;
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
	private Mapper mapper;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AuthenticationProvider openIDAuthenticationProvider;

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
			@RequestParam(value = "error", required = false) String error,
			RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		if (error != null) {
			return "redirect:/login";
		}
		System.out.println("code: " + code);
		RegisterUserForm registerUser = convertRegisterUserFormByFacebookCode(code);
		TAccount user = accountService.findByFacebookId(registerUser.getThirdPartyId());
		return goToLoginOrRegister(redirectAttributes, request, registerUser, user);
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
	 * go to Home or register page
	 *
	 * @param redirectAttributes redirectAttributes
	 * @param request            request
	 * @param registerUser       RegisterUserForm
	 * @param user               db user
	 * @return String
	 */
	private String goToLoginOrRegister(RedirectAttributes redirectAttributes, HttpServletRequest request,
			RegisterUserForm registerUser, TAccount user) {
		if (user == null) {
			// ユーザー登録
			AccountCreateForm form = new AccountCreateForm();
			form.setLoginId(registerUser.getThirdPartyId()); // TODO ログインID検討
			form.setMail(registerUser.getUserMail());
			form.setPassword(registerUser.getUserType().name() + "," + registerUser.getThirdPartyId() + ",USER"); // TODO 仮パスワード長すぎ
			accountService.create(form);
		}
		// TODO BANされている場合

		// ログイン
		String account = registerUser.getUserType().name() + "," + registerUser.getThirdPartyId() + ",USER";
		OpenIDAuthenticationToken token = new OpenIDAuthenticationToken(OpenIDAuthenticationStatus.SUCCESS, account,
				"", new ArrayList<>());
		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = openIDAuthenticationProvider.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

		//            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//            request.getSession().setAttribute(ApplicationConstant.USERNAME_KEY, userDetails.getAccount().getUsername());
		//            request.getSession().setAttribute(ApplicationConstant.USERIMGURL_KEY, userDetails.getAccount().getUserImgUrl());
		return "redirect:" + appConfig.getUrl();
	}

}
