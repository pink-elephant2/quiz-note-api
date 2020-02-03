package com.api.note.quiz.service.impl;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.api.note.quiz.config.AppConfig;
import com.api.note.quiz.form.AccountCreateForm;
import com.api.note.quiz.form.ContactForm;
import com.api.note.quiz.service.MailService;

/**
 * メールサービス
 */
@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private JavaMailSender javaMailSender;

	/** 送信元メールアドレス */
	@Value("${spring.mail.username}")
	private String fromMail;

	/** アカウント登録完了メールテンプレート */
	private final String MAIL_TEMPLATE_ACCOUNT_REGIST_COMPLETE = "account_regist_complete";

	/** お問い合わせ完了メールテンプレート */
	private final String MAIL_TEMPLATE_CONTACT_COMPLETE = "contact_complete";

	/** パスワードリマインダーメールテンプレート */
	private final String MAIL_TEMPLATE_ACCOUNT_PASSWORD_REMINDER = "account_password_reminder";

	/**
	 * アカウント登録完了メールを送信する
	 *
	 * @param form
	 *            アカウント作成フォーム
	 */
	public boolean sendAccountRegistComplete(AccountCreateForm form) {
		// 可変情報
		Context context = new Context();
		context.setVariable("app_title", appConfig.getAppName());
		context.setVariable("app_contact_url", getContactUrl());
		context.setVariable("name", form.getLoginId());
		context.setVariable("mail", form.getMail());
		context.setVariable("password", form.getPasswordMasked());

		MimeMessagePreparator message = createMimeMessagePreparator(form.getMail(), "アカウント登録完了のお知らせ", context,
				MAIL_TEMPLATE_ACCOUNT_REGIST_COMPLETE);

		javaMailSender.send(message);

		return true;
	}

	/**
	 * お問合せ完了メールを送信する
	 *
	 * @param form
	 *            お問合せフォーム
	 */
	@Override
	public boolean sendContactComplete(ContactForm form) {
		// 可変情報
		Context context = new Context();
		context.setVariable("app_title", appConfig.getAppName());
		context.setVariable("app_contact_url", getContactUrl());
		context.setVariable("name", form.getName());
		context.setVariable("content", form.getContent()); // TODO 改行コードを<br>に変換

		MimeMessagePreparator message = createMimeMessagePreparator(form.getMail(), "お問い合わせ受付のお知らせ", context,
				MAIL_TEMPLATE_CONTACT_COMPLETE);

		javaMailSender.send(message);

		return true;
	}

	/**
	 * パスワードリマインダーメールを送信する
	 *
	 * @param mail メールアドレス
	 * @param token ワンタイムトークン
	 */
	@Override
	public boolean sendPasswordReminder(@NotNull String mail, @NotNull String token) {
		// 可変情報
		Context context = new Context();
		context.setVariable("app_title", appConfig.getAppName());
		context.setVariable("app_contact_url", getContactUrl());
		context.setVariable("name", mail);
		context.setVariable("url", appConfig.getUrl() + "reminder/cbk?token=" + token);

		MimeMessagePreparator message = createMimeMessagePreparator(mail, "パスワードの変更リクエスト受付のお知らせ", context,
				MAIL_TEMPLATE_ACCOUNT_PASSWORD_REMINDER);

		javaMailSender.send(message);

		return true;
	}

	/**
	 * お問い合わせページのURLを返却する
	 *
	 * @return https://ドメイン/contact
	 */
	private String getContactUrl() {
		return appConfig.getUrl() + "contact";
	}

	/**
	 * メールを生成する
	 *
	 * @param toMail 宛先
	 * @param subject タイトル
	 * @param context 可変情報
	 * @param template テンプレート
	 * @return
	 */
	private MimeMessagePreparator createMimeMessagePreparator(String toMail, String subject, Context context,
			String template) {
		return new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
				helper.setFrom(fromMail);
				helper.setTo(toMail);
				helper.setSubject("『" + appConfig.getAppName() + "』 " + subject);

				SpringTemplateEngine templateEngine = new SpringTemplateEngine();
				ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
				templateResolver.setTemplateMode(TemplateMode.HTML);
				templateResolver.setPrefix("mailtemplate/");
				templateResolver.setSuffix(".html");
				templateResolver.setCharacterEncoding("UTF-8");
				templateResolver.setCacheable(true);
				templateEngine.setTemplateResolver(templateResolver);
				String text = templateEngine.process(template, context);
				helper.setText(text, true);
			}
		};
	}
}
