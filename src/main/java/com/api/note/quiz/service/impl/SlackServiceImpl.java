package com.api.note.quiz.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.note.quiz.config.AppConfig;
import com.api.note.quiz.form.ContactForm;
import com.api.note.quiz.service.SlackService;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

/**
 * Slackサービス
 */
@Service
public class SlackServiceImpl implements SlackService {

	@Autowired
	private AppConfig appConfig;

	/**
	 * お問合せ内容を送信する
	 *
	 * @param form
	 *            お問合せフォーム
	 */
	public boolean sendContactComplete(ContactForm form) {
		try {
			// BotのAPI Tokenを設定
			SlackSession session = SlackSessionFactory.createWebSocketSlackSession(appConfig.getSlackAppToken());
			session.connect();

			SlackChannel channel = session.findChannelByName(appConfig.getSlackContactChannel());

			StringBuilder sb = new StringBuilder();
			sb.append("@channel\n【"); // TODO FIXME メンションにならない
			sb.append(appConfig.getAppName());
			sb.append("】お問い合わせがありました。\n");
			sb.append("\n名前: ");
			sb.append(form.getName());
			sb.append("\nメールアドレス: ");
			sb.append(form.getMail());
			sb.append("\n内容: ");
			sb.append(form.getContent());

			session.sendMessage(channel, sb.toString());

			session.disconnect();
		} catch (IOException e) {
			// TODO エラー処理
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
