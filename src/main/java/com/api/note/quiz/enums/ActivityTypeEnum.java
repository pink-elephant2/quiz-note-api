package com.api.note.quiz.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

/**
 * 行動種別
 */
@Getter
public enum ActivityTypeEnum implements BaseEnum {

	/** コメント */
	//	COMMENT("Comment", "コメント", 1),
	/** いいね */
	LIKE("Like", "いいね", 2),
	/** フォロー */
	FOLLOW("Follow", "フォロー", 3),
	/** 新しい投稿 */
	NEW_POST("New Post", "新しい投稿", 4);
	/** コメントいいね */
	//	COMMENT_LIKE("Comment Like", "コメントいいね", 5);

	/** ステータス名称（英語） */
	private final String nameEn;

	/** ステータス名称（日本語） */
	private final String nameJa;

	/** ソート番号 */
	@JsonValue
	private final Integer sortOrder;

	ActivityTypeEnum(String nameEn, String nameJa, Integer sortOrder) {
		this.nameEn = nameEn;
		this.nameJa = nameJa;
		this.sortOrder = sortOrder;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	/** コメントか */
	//	public boolean isComment() {
	//		return this == COMMENT;
	//	}

	/** いいねか */
	public boolean isLike() {
		return this == LIKE;
	}

	/** フォローか */
	public boolean isFollow() {
		return this == FOLLOW;
	}

	/** 新しい投稿か */
	public boolean isNewPost() {
		return this == NEW_POST;
	}

	/** コメントいいねか */
	//	public boolean isCommentLike() {
	//		return this == COMMENT_LIKE;
	//	}
}
