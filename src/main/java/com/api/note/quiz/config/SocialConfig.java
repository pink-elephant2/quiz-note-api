package com.api.note.quiz.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import com.api.note.quiz.signup.DemoUserConnectionSignUp;
import com.api.note.quiz.signup.SignupService;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

	private final DataSource dataSource;
	private final SignupService signupService;

	public SocialConfig(DataSource dataSource, SignupService signupService) {
		this.dataSource = dataSource;
		this.signupService = signupService;
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(
				dataSource, connectionFactoryLocator, Encryptors.noOpText());
		repository.setConnectionSignUp(new DemoUserConnectionSignUp(signupService));
		return repository;
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}
}
