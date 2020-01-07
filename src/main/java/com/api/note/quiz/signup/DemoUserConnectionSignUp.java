package com.api.note.quiz.signup;

import org.apache.commons.lang3.StringUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

/**
 * 暗黙的サインアップ
 * @author kawasaki
 *
 */
public class DemoUserConnectionSignUp implements ConnectionSignUp {

	private final SignupService signupService;

	public DemoUserConnectionSignUp(SignupService signupService) {
		this.signupService = signupService;
	}

	/**
	 * 各プロバイダから取得したusernameが重複するときはnullを返した方がいいのだろうか。。
	 * @link <a href="http://docs.spring.io/spring-social/docs/2.0.0.M4/reference/htmlsingle/#implicit-sign-up">implicit-sign-up</a>
	 */
	@Override
	public String execute(Connection<?> connection) {
		// プロバイダからユーザー情報取得
		UserProfile profile = connection.fetchUserProfile();

		// プロバイダから取得した情報を元にローカルユーザー情報作成
//		DemoUser demoUser = signupService.createDemoUser(
//				 generateLoginId(profile)
//				,generatePassword()
//				,profile.getFirstName()
//				,profile.getLastName());
		// TODO 実装
		return "my_melody";

		// 自動生成されたローカルユーザーID返却
		// このユーザーIDがUserConnectionテーブルPKとして使用される
//		return demoUser.getUserId();
	}

	/**
	 * 仮パスワード発行
	 * @return
	 */
	private String generatePassword() {
//		RandomStringGenerator generator = new RandomStringGenerator.Builder()
//				.withinRange('0', 'z')
//				.filteredBy(LETTERS, DIGITS)
//				.build();
//
//		return generator.generate(8);

		// TODO 実装
		return "aaa";
	}

	/**
	 * 一旦、簡易的に。。
	 * @param profile
	 * @return
	 */
	private String generateLoginId(UserProfile profile) {
		if (StringUtils.isNotEmpty(profile.getEmail())) {
			return profile.getEmail();
		}
		if (StringUtils.isNotEmpty(profile.getUsername())) {
			return profile.getUsername();
		}
		if (StringUtils.isNotEmpty(profile.getName())) {
			return profile.getName();
		}
		// Exception
		return null;
	}
}
