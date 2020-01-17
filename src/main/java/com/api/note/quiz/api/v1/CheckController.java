package com.api.note.quiz.api.v1;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.note.quiz.exception.NotFoundException;

/**
 * チェック系API
 */
@RestController
@RequestMapping("/api/v1/check")
public class CheckController {

	/**
	 * ログインチェック
	 * @return ログインID
	 */
	@GetMapping("/login")
	public String check() {
		if (SecurityContextHolder.getContext() == null
				|| SecurityContextHolder.getContext().getAuthentication() == null
				|| SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
			// TODO 401
			throw new NotFoundException("未ログイン");
		}
		return "\"" + SecurityContextHolder.getContext().getAuthentication().getName() + "\"";
	}


}
