package com.api.note.quiz.api.v1;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.note.quiz.form.ContactForm;
import com.api.note.quiz.service.ContactService;


/**
 * お問合せAPI
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/contact")
public class ContactController {

	@Autowired
	private ContactService contactService;

	/**
	 * お問合せ登録
	 *
	 * @param form
	 *            お問合せフォーム
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public boolean contact(@RequestBody @Validated ContactForm form, HttpServletRequest request) {
		// TODO IPアドレスチェック
		System.out.println(request.getRemoteAddr());

		return contactService.save(form);
	}
}
