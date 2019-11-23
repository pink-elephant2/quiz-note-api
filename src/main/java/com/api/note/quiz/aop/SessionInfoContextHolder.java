package com.api.note.quiz.aop;

import org.springframework.stereotype.Component;

import com.api.note.quiz.resources.SessionInfoResource;

/**
 * セッション情報のコンテキストホルダ
 */
@Component("sctx")
public class SessionInfoContextHolder {

	/** コンテキスト（スレッド毎）. */
	private static ThreadLocal<SessionInfoResource> contextHolder = new ThreadLocal<SessionInfoResource>();

	/**
	 * データソースタイプ設定.
	 *
	 * @param sessionInfo
	 *            タイプ
	 */
	public static void setSessionInfo(SessionInfoResource sessionInfo) {
		contextHolder.set(sessionInfo);
	}

	/**
	 * データソースタイプ取得.
	 *
	 * @return dataSourceType タイプ
	 */
	public static SessionInfoResource getSessionInfo() {
		return contextHolder.get();
	}

	/**
	 * クリア.
	 */
	public static void clear() {
		contextHolder.remove();
	}

	/**
	 * ログインしているか
	 */
	public static boolean isAuthenticated() {
		return getSessionInfo() != null;
	}
}