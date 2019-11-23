package com.api.common.util;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
	private static AtomicBoolean globallyCanAutoSetUserId = new AtomicBoolean(true);

	public static void disableGloballyAutoSetUserId() {
		globallyCanAutoSetUserId.set(false);
	}

	public static void enableGloballyAutoSetUserId() {
		globallyCanAutoSetUserId.set(true);
	}

	public static boolean canGloballyAutoSetUserId() {
		return globallyCanAutoSetUserId.get();
	}

	public static String getLoginId() {
		return SecurityContextHolder.getContext() != null
				&& SecurityContextHolder.getContext().getAuthentication() != null
						? SecurityContextHolder.getContext().getAuthentication().getName() : null;
	}
}
