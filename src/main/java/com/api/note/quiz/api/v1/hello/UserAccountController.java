package com.api.note.quiz.api.v1.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.note.quiz.form.AccountImageForm;
import com.api.note.quiz.form.AccountUpdateForm;
import com.api.note.quiz.form.FollowForm;
import com.api.note.quiz.service.AccountService;
import com.api.note.quiz.service.FollowService;

/**
 * (認証必須)アカウントAPI TODO インターセプタで自分のログインIDかチェックする
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/user/{loginId}/account")
public class UserAccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private FollowService followService;

	/**
	 * プロフィール更新
	 *
	 * @param form
	 *            プロフィールフォーム
	 */
	@PostMapping("/profile")
	@ResponseStatus(HttpStatus.CREATED)
	public boolean saveProfile(@RequestBody @Validated AccountUpdateForm form) {
		// プロフィールを更新する
		return accountService.saveProfile(form);
	}

	/**
	 * アカウント画像更新
	 */
	@PostMapping("/image")
	@ResponseStatus(HttpStatus.CREATED)
	public boolean saveImage(@Validated AccountImageForm form) {
		// アカウント画像を更新する
		return accountService.saveImage(form);
	}

	/**
	 * フォローする
	 *
	 * @param loginId
	 *            ログインID
	 */
	@PostMapping("/follow")
	@ResponseStatus(HttpStatus.CREATED)
	public boolean follow(@RequestBody @Validated FollowForm form) throws Exception {
		// フォローする
		return followService.follow(form.getLoginId());
	}

	/**
	 * フォローを解除する
	 *
	 * @param loginId
	 *            ログインID
	 */
	@PostMapping("/unfollow")
	@ResponseStatus(HttpStatus.CREATED)
	public boolean unfollow(@RequestBody @Validated FollowForm form) throws Exception {
		// フォローを解除する
		return followService.unfollow(form.getLoginId());
	}
}
