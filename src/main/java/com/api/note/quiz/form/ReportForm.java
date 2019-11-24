package com.api.note.quiz.form;

import javax.validation.constraints.NotNull;

import com.api.note.quiz.enums.ReportReasonEnum;

import lombok.Data;

/**
 * 通報フォーム
 */
@Data
public class ReportForm {

	/** 理由 */
	@NotNull
	private ReportReasonEnum reason;
}
