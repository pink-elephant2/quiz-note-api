package com.api.note.quiz.consts;

/**
 * 共通定数クラス
 */
public class CommonConst {

	/**
	 * システムアカウント
	 */
	public static final class SystemAccount {
		/** 管理者アカウントID */
		public static final String ADMIN_ID = "admin";
		/** ゲストアカウントID */
		public static final String GUEST_ID = "guest";
		/** バッチアカウントID */
		public static final String BATCH_ID = "batch";
	}

	/**
	 * 削除フラグ
	 */
	public static final class DeletedFlag {
		/** 未削除 */
		public static final String OFF = "0";
		/** 削除済み */
		public static final String ON = "1";
	}
}