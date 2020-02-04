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
import com.api.note.quiz.exception.NotFoundException;
import com.api.note.quiz.form.PasswordReminderForm;
import com.api.note.quiz.form.PasswordResetForm;
import com.api.note.quiz.service.AccountService;
import com.api.note.quiz.service.CacheService;
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
	private CacheService cacheService;

	@Autowired
	private MailService mailService;

	@Autowired
	private AppConfig appConfig;

	/** キャッシュキー */
	private final String CACHE_KEY_PREFIX = "FORGET_ID_KEY:";

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
		String secretKey = RandomStringUtils.randomAlphanumeric(16);
		String token;
		try {
			String key = account.getLoginId() + "," + secretKey;
			String signature = AesUtils.encrypt(appConfig.getAesKey(), key);
			token = URLEncoder.encode(signature, "utf-8");
		} catch (Exception e) {
			throw e;
		}

		// キャッシュ(24時間)
		cacheService.saveValue(CACHE_KEY_PREFIX + account.getLoginId(), secretKey, 86400);

		// リマインダーメール送信
		return mailService.sendPasswordReminder(form.getMail(), token);
	}

	/**
	 * ワンタイムトークンをチェックする
	 *
	 * @param token ワンタイムトークン
	 */
	@GetMapping("/reminder/token")
	public boolean check(String token) {
		// トークンチェック
		return checkToken(token);
	}

	/**
	 * パスワードを再設定する
	 */
	@PostMapping("/reset")
	public boolean reset(@RequestBody @Validated PasswordResetForm form) {
		// トークンチェック
		if (!checkToken(form.getToken())) {
			// 有効期限切れなど
			throw new NotFoundException("");
		}
		// パスワード更新
		return accountService.savePassword(form);
	}

	/**
	 * トークンチェック
	 */
	private boolean checkToken(String token) {
		String key;
		try {
			key = AesUtils.decrypt(appConfig.getAesKey(), token);
		} catch (Exception e) {
			// 不正なアクセス
			throw new NotFoundException("");
		}
		String[] tempArray = key.split(",");
		String loginId = tempArray[0];
		String secretKey = tempArray[1];
		String secretKeyCache = cacheService.getValue(CACHE_KEY_PREFIX + loginId);
		if (secretKeyCache == null) {
			// 有効期限切れ
			throw new NotFoundException("");
		}
		if (!secretKey.equals(secretKeyCache)) {
			// 不正なアクセス
			throw new NotFoundException("");
		}
		return true;
	}
}
