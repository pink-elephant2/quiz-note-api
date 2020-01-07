package com.api.note.quiz.api.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * ログイン画面
 */
@Controller
@RequestMapping("/signin")
public class SigninController {

	/**
	 * アカウント登録
	 *
	 * @param form
	 *            ログインID
	 */
	@GetMapping
	public ModelAndView signin(ModelAndView model) {
		System.out.println("signin!!");
		model.setViewName("signin");
		return model;
	}
}
