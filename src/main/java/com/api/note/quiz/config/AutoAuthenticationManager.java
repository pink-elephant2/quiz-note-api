package com.api.note.quiz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.api.note.quiz.domain.TAccount;
import com.api.note.quiz.domain.TAccountExample;
import com.api.note.quiz.repository.TAccountRepository;

/**
 * 独自ログイン
 *
 * FIXME もっと良いやり方
 */
@Component
public class AutoAuthenticationManager implements AuthenticationManager {

	@Autowired
	private TAccountRepository tAccountRepository;

	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		TAccountExample example = new TAccountExample();
		example.createCriteria().andLoginIdEqualTo(auth.getPrincipal().toString())
				.andFacebookEqualTo(auth.getPrincipal().toString());
		TAccount account = tAccountRepository.findOneBy(example);

		// Facebook認証済みのため、ユーザー存在チェックのみ
		if (account != null) {
			return new UsernamePasswordAuthenticationToken(auth.getPrincipal(),
					auth.getCredentials(), AuthorityUtils.createAuthorityList("ROLE_USER"));
		}
		throw new BadCredentialsException("Bad Credentials");
	}
}
