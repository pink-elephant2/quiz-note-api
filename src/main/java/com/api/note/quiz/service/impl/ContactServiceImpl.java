package com.api.note.quiz.service.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.note.quiz.domain.TContact;
import com.api.note.quiz.form.ContactForm;
import com.api.note.quiz.repository.TContactRepository;
import com.api.note.quiz.service.ContactService;
import com.api.note.quiz.service.MailService;


/**
 * お問合せサービス
 */
@Service
@Transactional
public class ContactServiceImpl implements ContactService {

	@Autowired
	private TContactRepository tContactRepository;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MailService mailService;

	/**
	 * お問合せ登録する
	 *
	 * @param form
	 *            お問合せフォーム
	 */
	@Override
	public boolean save(ContactForm form) {

		// DB登録
		TContact contact = mapper.map(form, TContact.class);
		contact.setReadFlag(false);
		tContactRepository.create(contact);

		// TODO 運営にメール送信

		// サンキューメール送信
		mailService.sendContactComplete(form);

		return true;
	}
}
