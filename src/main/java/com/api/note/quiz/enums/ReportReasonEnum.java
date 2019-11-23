package com.api.note.quiz.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

/**
 * 通報理由
 */
@Getter
public enum ReportReasonEnum implements BaseEnum {

	/** 気に入らない */
	DISLIKE("Dislike", "気に入らない", 1),
	/** スパム */
	SPAM("Spam", "スパム", 2),
	/** わいせつ */
	ADULT("Adult", "わいせつなコンテンツ", 3),
	/** 差別的な内容 */
	DISCRIMINATION("Discrimination", "差別的な内容", 4),
	/** その他 */
	OTHER("Other", "その他", 9);

	/** ステータス名称（英語） */
	private final String nameEn;

	/** ステータス名称（日本語） */
	private final String nameJa;

	/** ソート番号 */
	@JsonValue
	private final Integer sortOrder;

	ReportReasonEnum(String nameEn, String nameJa, Integer sortOrder) {
		this.nameEn = nameEn;
		this.nameJa = nameJa;
		this.sortOrder = sortOrder;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	/** 気に入らないか */
	public boolean isDislike() {
		return this == DISLIKE;
	}

	/** スパムか */
	public boolean isSpam() {
		return this == SPAM;
	}

	/** わいせつか */
	public boolean isAdult() {
		return this == ADULT;
	}

	/** 差別的な内容か */
	public boolean isDiscrimination() {
		return this == DISCRIMINATION;
	}

	/** その他か */
	public boolean isOther() {
		return this == OTHER;
	}
}
