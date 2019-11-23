package com.api.note.quiz.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

/**
 * 通報対象
 */
@Getter
public enum ReportTargetEnum implements BaseEnum {

	/** アカウント */
	ACCOUNT("Account", "アカウント", 1),
	/** クイズ */
	QUIZ("Quiz", "クイズ", 2);

	/** ステータス名称（英語） */
	private final String nameEn;

	/** ステータス名称（日本語） */
	private final String nameJa;

	/** ソート番号 */
	@JsonValue
	private final Integer sortOrder;

	ReportTargetEnum(String nameEn, String nameJa, Integer sortOrder) {
		this.nameEn = nameEn;
		this.nameJa = nameJa;
		this.sortOrder = sortOrder;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	/** アカウントか */
	public boolean isAccount() {
		return this == ACCOUNT;
	}

	/** クイズか */
	public boolean isQuiz() {
		return this == QUIZ;
	}
}
