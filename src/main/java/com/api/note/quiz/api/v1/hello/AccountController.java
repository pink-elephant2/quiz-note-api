package com.api.note.quiz.api.v1.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.note.quiz.form.AccountCreateForm;
import com.api.note.quiz.form.ReportForm;
import com.api.note.quiz.resources.AccountResource;
import com.api.note.quiz.service.AccountService;
import com.api.note.quiz.service.FollowService;

/**
 * アカウントAPI
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private FollowService followService;

	/**
	 * アカウント登録
	 *
	 * @param form
	 *            ログインID
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public boolean save(@RequestBody @Validated AccountCreateForm form) {
		// アカウントを登録する
		return accountService.create(form);
	}

	/**
	 * アカウント取得
	 *
	 * @param loginId
	 *            ログインID
	 */
	@GetMapping("/{loginId}")
	@ResponseStatus(HttpStatus.OK)
	public AccountResource find(@PathVariable("loginId") String loginId) {
		// アカウントを取得する
		return accountService.find(loginId);
	}

	/**
	 * アカウント通報
	 *
	 * @param loginId
	 *            ログインID
	 */
	@PostMapping("/{loginId}/report")
	@ResponseStatus(HttpStatus.OK)
	public boolean report(@PathVariable("loginId") String loginId, @RequestBody @Validated ReportForm form) {
		// アカウントを通報する
		return accountService.report(loginId, form.getReason());
	}

	/**
	 * アカウントブロック
	 *
	 * @param loginId
	 *            ログインID
	 */
	@PostMapping("/{loginId}/block")
	@ResponseStatus(HttpStatus.OK)
	public boolean block(@PathVariable("loginId") String loginId) {
		// アカウントをブロックする
		return accountService.block(loginId);
	}

	/**
	 * フォロー取得
	 *
	 * @param loginId
	 *            ログインID
	 */
	@GetMapping("/{loginId}/follow")
	@ResponseStatus(HttpStatus.OK)
	public Page<AccountResource> findFollow(@PathVariable("loginId") String loginId, Pageable pageable) {
		// フォローを取得する
		return followService.findFollow(loginId, pageable);
	}

	/**
	 * フォローワー取得
	 *
	 * @param loginId
	 *            ログインID
	 */
	@GetMapping("/{loginId}/follower")
	@ResponseStatus(HttpStatus.OK)
	public Page<AccountResource> findFollower(@PathVariable("loginId") String loginId, Pageable pageable) {
		// フォローワーを取得する
		return followService.findFollowers(loginId, pageable);
	}
}
