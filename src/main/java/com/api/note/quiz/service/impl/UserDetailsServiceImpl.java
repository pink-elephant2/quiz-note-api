package com.api.note.quiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.note.quiz.domain.TAccount;
import com.api.note.quiz.exception.NotFoundException;
import com.api.note.quiz.repository.TAccountRepository;
import com.api.note.quiz.resources.SessionInfoResource;

/**
 * ユーザー認証サービス
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private TAccountRepository tAccountRepository;

	/**
	 * 認証
	 */
	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		// DB検索
		try {
			TAccount account = tAccountRepository.findOneByLoginId(loginId);

			if (account != null) {
				return new User(loginId, account.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
			}
		} catch (NotFoundException e) {

		}
		throw new UsernameNotFoundException("not found : " + loginId);
	}

	/**
	 * 認証済みかチェックする
	 */
	public SessionInfoResource authenticated() {
		// ログインセッションが取得できない場合はエラー
		if (SecurityContextHolder.getContext() == null || SecurityContextHolder.getContext().getAuthentication() == null
				|| SecurityContextHolder.getContext().getAuthentication().getName() == null) {
			return null;
		}

		// DB検索
		String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
		TAccount account = tAccountRepository.findOneByLoginId(loginId);

		// ユーザがいない場合はエラー
		if (account == null) {
			return null;
		}

		// セッション情報(HttpSession)
		SessionInfoResource sessionInfo = new SessionInfoResource();
		sessionInfo.setAccountId(account.getAccountId());
		sessionInfo.setLoginId(loginId);
		sessionInfo.setUserName(account.getName());

		return sessionInfo;
	}
}
