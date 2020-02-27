package com.api.note.quiz.api.v1;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
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

import com.api.note.quiz.form.ReportForm;
import com.api.note.quiz.resources.QuizResource;
import com.api.note.quiz.service.QuizService;

/**
 * クイズAPI
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;

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
	public Page<QuizResource> findList(@Validated @NotNull String loginId, @SortDefault.SortDefaults({
			@SortDefault(sort = "quiz_id", direction = Direction.DESC) }) Pageable pageable) {
		// クイズ一覧を取得する
		return quizService.findList(loginId, pageable);
	}

	/**
	 * クイズ通報
	 *
	 * @param cd
	 *            コード
	 */
	@PostMapping("/{cd}/report")
	@ResponseStatus(HttpStatus.OK)
	public boolean report(@PathVariable("cd") String cd, @RequestBody @Validated ReportForm form) {
		// クイズを通報する
		return quizService.report(cd, form.getReason());
	}
}
