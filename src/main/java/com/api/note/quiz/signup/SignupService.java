package com.api.note.quiz.signup;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO AccountService
 */
@Service
@Transactional(readOnly = true)
public class SignupService {
//	private final DemoUserRepository demoUserRepository;
//
//	public SignupService(DemoUserRepository demoUserRepository) {
//		this.demoUserRepository = demoUserRepository;
//	}
//
//	@Transactional(readOnly = false)
//	public DemoUser createDemoUser(String loginId, String password, String firstName, String lastName) {
//		DemoUser demoUser = new DemoUser();
//		demoUser.setLoginId(loginId);
//		demoUser.setPassword(password);
//		demoUser.setFirstName(firstName);
//		demoUser.setLastName(lastName);
//		demoUser.setAccountNonLocked(true);
//		demoUser.setEnabled(true);
//
//		// userIdの自動生成のために一旦save
//		demoUser = demoUserRepository.save(demoUser);
//
//		AuthoritiesPK authoritiesPK = new AuthoritiesPK();
//		authoritiesPK.setUserId(demoUser.getUserId());
//		authoritiesPK.setAuthority(RoleType.ROLE_USER);
//
//		Authorities authorities = new Authorities();
//		authorities.setKey(authoritiesPK);
//		List<Authorities> authList = new ArrayList<>();
//		authList.add(authorities);
//
//		demoUser.setAuthorities(authList);
//
//		return demoUserRepository.save(demoUser);
//	}
}
