package com.api.note.quiz.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

/**
 * 公開種別
 */
@Getter
public enum ScopeTypeEnum implements BaseEnum {

	/** 非公開 */
	PRIVATE("Private", "非公開", 1),
	/** グループ公開 */
	GROUP("Group", "グループ公開", 2),
	/** 全体公開 */
	PUBLIC("Public", "全体公開", 3);

	/** ステータス名称（英語） */
	private final String nameEn;

	/** ステータス名称（日本語） */
	private final String nameJa;

	/** ソート番号 */
	@JsonValue
	private final Integer sortOrder;

	ScopeTypeEnum(String nameEn, String nameJa, Integer sortOrder) {
		this.nameEn = nameEn;
		this.nameJa = nameJa;
		this.sortOrder = sortOrder;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	/** 非公開か */
	public boolean isPrivate() {
		return this == PRIVATE;
	}

	/** グループ公開か */
	public boolean isGroup() {
		return this == GROUP;
	}

	/** 全体公開か */
	public boolean isPublic() {
		return this == PUBLIC;
	}
}
