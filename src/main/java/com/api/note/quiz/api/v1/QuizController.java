package com.api.note.quiz.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.note.quiz.form.ReportForm;
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
