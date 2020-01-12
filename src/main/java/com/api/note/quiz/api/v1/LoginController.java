package com.api.note.quiz.api.v1;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.api.note.quiz.aop.SessionInfoContextHolder;
import com.api.note.quiz.config.AppConfig;
import com.api.note.quiz.domain.TAccount;
import com.api.note.quiz.domain.TAccountExample;
import com.api.note.quiz.enums.UserType;
import com.api.note.quiz.exception.NotFoundException;
import com.api.note.quiz.form.AccountCreateForm;
import com.api.note.quiz.form.RegisterUserForm;
import com.api.note.quiz.repository.TAccountRepository;
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
	private TAccountRepository tAccountRepository;

	private SampleAuthenticationManager am = null;

	/** TODO もっといいやり方 */
	class SampleAuthenticationManager implements AuthenticationManager {
		private TAccountRepository tAccountRepository;

		public SampleAuthenticationManager(TAccountRepository tAccountRepository) {
			this.tAccountRepository = tAccountRepository;
		}

		public Authentication authenticate(Authentication auth) throws AuthenticationException {
			TAccountExample example = new TAccountExample();
			example.createCriteria().andLoginIdEqualTo(auth.getPrincipal().toString())
					.andFacebookEqualTo(auth.getPrincipal().toString());
			TAccount account = tAccountRepository.findOneBy(example);

			// Facebook認証済みのため、ユーザー存在チェックのみ
			if (account != null) {
				return new UsernamePasswordAuthenticationToken(auth.getPrincipal(),
						auth.getCredentials(), AuthorityUtils.createAuthorityList("ROLE_USER"));
			}
			throw new BadCredentialsException("Bad Credentials");
		}
	}

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
		return SessionInfoContextHolder.getSessionInfo().getLoginId();
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
			@RequestParam(value = "error", required = false) String error,
			RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		if (error != null) {
			return "redirect:" + appConfig.getUrl();
		}
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
			form.setFacebook(registerUser.getThirdPartyId());
			accountService.create(form);
		}
		// TODO BANされている場合

		// ログイン
		String pass = registerUser.getUserType().name() + "," + registerUser.getThirdPartyId() + ",USER";
		UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
				registerUser.getThirdPartyId(), pass, AuthorityUtils.createAuthorityList("ROLE_USER"));
		authReq.setDetails(new WebAuthenticationDetails(request));
		am = new SampleAuthenticationManager(tAccountRepository);
		Authentication auth = am.authenticate(authReq);

		SecurityContextHolder.getContext().setAuthentication(auth);
		return "redirect:" + appConfig.getUrl();
	}

}
