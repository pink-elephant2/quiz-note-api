package com.api.note.quiz.api.v1;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.api.note.quiz.enums.Gender;
import com.api.note.quiz.enums.UserType;
import com.api.note.quiz.form.RegisterUserForm;
import com.api.note.quiz.resources.AccountResource;
import com.api.note.quiz.service.AccountService;
import com.api.note.quiz.service.FacebookService;
import com.restfb.types.User;

/**
 * ログインAPI
 */
@Controller
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private AccountService accountService;

//    @Autowired
//    private MessageSource messageSource;

//    @Autowired
//    private AuthenticationProvider openIDAuthenticationProvider;

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
        AccountResource user = accountService.findByFacebookId(registerUser.getThirdPartyId());
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
        if (user.getGender() != null && !user.getGender().equals("")) {
            registerUser.setUserGender(Gender.facebookGenderOf(user.getGender()).name());
        }
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
                                       RegisterUserForm registerUser, AccountResource user) {
        if (user == null) {
            // need register
            redirectAttributes.addFlashAttribute("registerUser", registerUser);
            return "redirect:/login/register";
        } else {
        	// TODO 実装
//            if (user.getUserStatus() == Status.PRIVATE) {
//                request.getSession().setAttribute(ApplicationConstant.ALERT_MESSAGE, messageSource.getMessage(MessageConstant.MESSAGE_ERROR_USERSTATUS_PRIVATE, null, LocaleContextHolder.getLocale()));
//                return "redirect:/login/select";
//            }
//
//            // login
//            String account = registerUser.getUserType().name() + "," + registerUser.getThirdPartyId() + "," + RoleType.USER.name();
//            OpenIDAuthenticationToken token = new OpenIDAuthenticationToken(OpenIDAuthenticationStatus.SUCCESS, account, "", new ArrayList<>());
//            token.setDetails(new WebAuthenticationDetails(request));
//            Authentication authenticatedUser = openIDAuthenticationProvider.authenticate(token);
//            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
//
//            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            request.getSession().setAttribute(ApplicationConstant.USERNAME_KEY, userDetails.getAccount().getUsername());
//            request.getSession().setAttribute(ApplicationConstant.USERIMGURL_KEY, userDetails.getAccount().getUserImgUrl());
            return "redirect:/";
        }
    }

}
