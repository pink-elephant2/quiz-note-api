package com.api.note.quiz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * ソーシャルユーザー認証サービス
 */
public class SocialUserDetailsServiceImpl implements SocialUserDetailsService {

	private final UserDetailsServiceImpl userRepository;

	public SocialUserDetailsServiceImpl(UserDetailsServiceImpl userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
//		DemoUser demoUser = demoUserRepository.findOne(userId);
//
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//		for(Authorities auth: demoUser.getAuthorities()){
//			authorities.add(new SimpleGrantedAuthority(auth.getKey().getAuthority().toString()));
//		}
//
//		return new SocialUser(demoUser.getUserId(), demoUser.getPassword(), authorities);

		// TODO 実装
		return new SocialUser("my_melody", "aaa", authorities);
	}

}
