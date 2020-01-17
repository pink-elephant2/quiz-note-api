package com.api.note.quiz.service.impl;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;

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

	/** お問い合わせ完了メールテンプレート */
	private final String MAIL_TEMPLATE_CONTACT_COMPLETE = "contact_complete";

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
		context.setVariable("name", form.getName());
		context.setVariable("content", form.getContent()); // TODO 改行コードを<br>に変換

		MimeMessagePreparator message = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
						StandardCharsets.UTF_8.name());
				helper.setFrom(fromMail);
				helper.setTo(form.getMail());
				helper.setSubject("『" + appConfig.getAppName() + "』 お問い合わせ受付のお知らせ");

				SpringTemplateEngine templateEngine = new SpringTemplateEngine();
				ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
				templateResolver.setTemplateMode(TemplateMode.HTML);
				templateResolver.setPrefix("mailtemplate/");
				templateResolver.setSuffix(".html");
				templateResolver.setCharacterEncoding("UTF-8");
				templateResolver.setCacheable(true);
				templateEngine.setTemplateResolver(templateResolver);
				String text = templateEngine.process(MAIL_TEMPLATE_CONTACT_COMPLETE, context);
				helper.setText(text, true);
			}
		};

		javaMailSender.send(message);

		return true;
	}
}
