package com.api.note.quiz.resources;

import java.util.Date;

import com.api.note.quiz.enums.ActivityTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * アクティビティAPIレスポンス
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityResource {

	/** 行動種別 */
	private ActivityTypeEnum activityType;

	/** クイズ */
	private QuizResource quiz;

	/** 日時 */
	private Date createdAt;

	/** ユーザー */
	private AccountResource account;
}
