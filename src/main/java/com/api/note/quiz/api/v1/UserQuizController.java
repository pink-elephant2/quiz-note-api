package com.api.note.quiz.api.v1;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.note.quiz.form.QuizCreateForm;
import com.api.note.quiz.form.QuizSoundForm;
import com.api.note.quiz.form.QuizUpdateForm;
import com.api.note.quiz.resources.QuizResource;
import com.api.note.quiz.service.QuizService;

/**
 * (認証必須)クイズAPI TODO インターセプタで自分のログインIDかチェックする
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/user/{loginId}/quiz")
public class UserQuizController {

	@Autowired
	private QuizService quizService;

	/**
	 * クイズ取得
	 *
	 * @param cd
	 *            コード
	 */
	//	@GetMapping("/{cd}")
	//	@ResponseStatus(HttpStatus.OK)
	//	public QuizResource find(@PathVariable("cd") String cd) {
	//		// クイズを取得する
	//		return quizService.find(cd);
	//	}

	/**
	 * クイズ一覧取得
	 *
	 * @param loginId
	 *            ログインID
	 * @param pageable
	 *            ページ情報
	 */
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<QuizResource> findList(String loginId, @SortDefault.SortDefaults({
			@SortDefault(sort = "quiz_id", direction = Direction.DESC) }) Pageable pageable) {

		if (StringUtils.isEmpty(loginId)) {
			loginId = SecurityContextHolder.getContext().getAuthentication().getName();
		}

		// クイズ一覧を取得する
		return quizService.findList(loginId, pageable);
	}

	/**
	 * クイズ登録
	 *
	 * @param form
	 *            クイズフォーム
	 * @return クイズ情報
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public QuizResource create(@RequestBody @Validated QuizCreateForm form) {
		// クイズを登録し、登録内容を返却する
		return quizService.create(form);
	}

	/**
	 * クイズ更新
	 *
	 * @param form
	 *            クイズフォーム
	 * @return クイズ情報
	 */
	@PutMapping
	@ResponseStatus(HttpStatus.CREATED)
	public QuizResource update(@RequestBody @Validated QuizUpdateForm form) {
		// クイズを更新し、登録内容を返却する
		return quizService.update(form);
	}

	/**
	 * 問読み登録
	 *
	 * @param form
	 *            クイズフォーム
	 * @return クイズ情報
	 */
	@PostMapping("/{cd}/sound")
	@ResponseStatus(HttpStatus.CREATED)
	public QuizResource updateSound(@PathVariable("cd") String cd, @Validated QuizSoundForm form) {
		// クイズを更新し、登録内容を返却する
		return quizService.updateSound(form);
	}

	/**
	 * クイズにいいねをする
	 *
	 * @param cd
	 *            コード
	 */
	@PostMapping("/{cd}/like")
	@ResponseStatus(HttpStatus.CREATED)
	public boolean like(@PathVariable("cd") String cd) {
		// いいねをする
		return quizService.like(cd, true);
	}

	/**
	 * クイズのいいねを解除する
	 *
	 * @param cd
	 *            コード
	 */
	@PostMapping("/{cd}/dislike")
	@ResponseStatus(HttpStatus.CREATED)
	public boolean dislike(@PathVariable("cd") String cd) {
		// いいねを解除する
		return quizService.like(cd, false);
	}

	/**
	 * クイズを削除する
	 *
	 * @param cd
	 *            コード
	 */
	@DeleteMapping("/{cd}")
	@ResponseStatus(HttpStatus.CREATED)
	public boolean remove(@PathVariable("cd") String cd) {
		// クイズを削除する
		return quizService.remove(cd);
	}
}
