package com.api.note.quiz.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "quiz")
@Data
public class AppConfig {

	/** アプリ名 */
	private String appName;

	/** サイトURL */
	private String url;

	/** S3アクセスキー */
	private String s3AccessKey;

	/** S3シークレットキー */
	private String s3SecretKey;

	/** S3サービスエンドポイント */
	private String s3ServiceEndPoint;

	/** S3リージョン */
	private String s3Region;

	/** S3バケット */
	private String s3Bucket;

	/** クラウドフロントホスト */
	private String cloudHostUrl;

	/** AWSアクセス時のタイムアウト時間(ms) */
	private Integer timeout;

	/** SlackAPIトークン (お問い合わせBot) */
	private String slackAppToken;

	/** Slackお問い合わせチャンネル */
	private String slackContactChannel;
}
