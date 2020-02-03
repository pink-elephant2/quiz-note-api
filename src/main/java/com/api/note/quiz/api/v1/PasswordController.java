package com.api.note.quiz.api.v1;

import java.net.URLEncoder;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.note.quiz.config.AppConfig;
import com.api.note.quiz.domain.TAccount;
import com.api.note.quiz.form.PasswordReminderForm;
import com.api.note.quiz.form.PasswordResetForm;
import com.api.note.quiz.service.AccountService;
import com.api.note.quiz.service.MailService;
import com.api.note.quiz.util.AesUtils;

/**
 * パスワードリマインダーAPI
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/password")
public class PasswordController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private MailService mailService;

	@Autowired
	private AppConfig appConfig;

	/**
	 * リマインダーメールを送信する
	 *
	 * @throws Exception
	 */
	@PostMapping("/reminder")
	public boolean reminder(@RequestBody @Validated PasswordReminderForm form) throws Exception {
		// ユーザー存在チェック
		TAccount account = accountService.findByMail(form.getMail());

		// ワンタイムトークン生成
		String token;
		try {
			String secretKey = RandomStringUtils.randomAlphanumeric(16);
			String key = account.getLoginId() + "," + secretKey;
			String signature = AesUtils.encrypt(appConfig.getAesKey(), key);
			token = URLEncoder.encode(signature, "utf-8");
		} catch (Exception e) {
			throw e;
		}

		// TODO キャッシュ(24時間)
		//		cacheService.saveString("FORGET_ID_KEY:" + account.getLoginId(), secretKey, 86400);

		// リマインダーメール送信
		return mailService.sendPasswordReminder(form.getMail(), token);
	}

	/**
	 * ワンタイムトークンをチェックする
	 *
	 * @param token ワンタイムトークン
	 */
	@GetMapping("/reminder/token")
	public boolean checkToken(String token) {
		// TODO 実装
		return true;
	}

	/**
	 * パスワードを再設定する
	 */
	@PostMapping("/reset")
	public boolean reset(@RequestBody @Validated PasswordResetForm form) {
		form.getMail();
		// TODO 実装
		return true;
	}
}
