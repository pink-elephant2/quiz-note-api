package com.api.note.quiz.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.note.quiz.aop.SessionInfoContextHolder;
import com.api.note.quiz.consts.CommonConst;
import com.api.note.quiz.domain.TAccount;
import com.api.note.quiz.domain.TAccountExample;
import com.api.note.quiz.domain.TBanReport;
import com.api.note.quiz.domain.TFollow;
import com.api.note.quiz.domain.TFollowExample;
import com.api.note.quiz.enums.DocumentTypeEnum;
import com.api.note.quiz.enums.ReportReasonEnum;
import com.api.note.quiz.enums.ReportTargetEnum;
import com.api.note.quiz.exception.NotFoundException;
import com.api.note.quiz.form.AccountCreateForm;
import com.api.note.quiz.form.AccountImageForm;
import com.api.note.quiz.form.AccountUpdateForm;
import com.api.note.quiz.repository.TAccountRepository;
import com.api.note.quiz.repository.TBanReportRepository;
import com.api.note.quiz.repository.TFollowRepository;
import com.api.note.quiz.resources.AccountResource;
import com.api.note.quiz.service.AccountService;
import com.api.note.quiz.service.S3Service;

/**
 * アカウントサービス
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private TAccountRepository tAccountRepository;

	@Autowired
	private TFollowRepository tFollowRepository;

	@Autowired
	private TBanReportRepository tBanReportRepository;

	@Autowired
	private Mapper mapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private S3Service s3Service;

	/**
	 * アカウントを登録する
	 *
	 * @param form
	 *            アカウント作成フォーム
	 */
	@Override
	public boolean create(AccountCreateForm form) {
		// アカウントを登録する
		TAccount account = mapper.map(form, TAccount.class);
		account.setName(form.getLoginId());

		// パスワード生成
		account.setPassword(passwordEncoder.encode(form.getPassword()));
		account.setPasswordChangeDate(new Date());

		// TODO エラーメッセージ
		return tAccountRepository.create(account);
	}

	/**
	 * アカウントを取得する
	 *
	 * @param loginId
	 *            ログインID
	 * @return アカウント情報
	 */
	@Override
	public AccountResource find(String loginId) {
		TAccount account = tAccountRepository.findOneByLoginId(loginId);

		if (account == null) {
			throw new NotFoundException("アカウントが存在しません");
		}
		AccountResource resource = mapper.map(account, AccountResource.class);

		// ログイン済みの場合
		if (SessionInfoContextHolder.isAuthenticated()
				&& !SessionInfoContextHolder.getSessionInfo().getAccountId().equals(account.getAccountId())) {
			// フォローしているか
			TFollowExample followExample = new TFollowExample();
			followExample.createCriteria().andAccountIdEqualTo(SessionInfoContextHolder.getSessionInfo().getAccountId())
					.andFollowAccountIdEqualTo(account.getAccountId()).andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
			TFollow follow = tFollowRepository.findOneBy(followExample);

			resource.setFollow(follow != null);

			// フォローされているか
			TFollowExample followerExample = new TFollowExample();
			followerExample.createCriteria().andAccountIdEqualTo(account.getAccountId())
					.andFollowAccountIdEqualTo(SessionInfoContextHolder.getSessionInfo().getAccountId());
			TFollow follower = tFollowRepository.findOneBy(followerExample);

			if (follower != null) {
				resource.setFollower(CommonConst.DeletedFlag.OFF.equals(follower.getDeleted()));
				resource.setBlocked(BooleanUtils.toBoolean(follower.getBlockFlag()));
			}
		}

		return resource;
	}

	/**
	 * アカウントを通報する
	 *
	 * @param loginId
	 *            ログインID
	 * @param reason
	 *            理由
	 */
	@Override
	public boolean report(String loginId, ReportReasonEnum reason) {
		TAccount account = tAccountRepository.findOneByLoginId(loginId);

		// レコード登録
		TBanReport entity = new TBanReport();
		entity.setReportTarget(ReportTargetEnum.ACCOUNT);
		entity.setReason(reason);
		entity.setAccountId(account.getAccountId());
		entity.setReadFlag(false);
		entity.setDoneFlag(false);
		return tBanReportRepository.create(entity);
	}

	/**
	 * アカウントをブロックする
	 *
	 * @param loginId
	 *            ログインID
	 */
	@Override
	public boolean block(String loginId) {
		if (!SessionInfoContextHolder.isAuthenticated()) {
			return false;
		}

		TAccount account = tAccountRepository.findOneByLoginId(loginId);
		Integer accountId = SessionInfoContextHolder.getSessionInfo().getAccountId();

		// ログイン済みの場合、フォローしているか
		TFollowExample followExample = new TFollowExample();
		followExample.createCriteria().andAccountIdEqualTo(accountId)
				.andFollowAccountIdEqualTo(account.getAccountId());
		TFollow follower = tFollowRepository.findOneBy(followExample);

		boolean ret;
		if (follower == null) {
			// 登録内容の設定
			TFollow follow = new TFollow();
			follow.setAccountId(accountId);
			follow.setFollowAccountId(account.getAccountId());
			follow.setBlockFlag(true);
			follow.setDeleted(CommonConst.DeletedFlag.ON);

			// レコード登録
			ret = tFollowRepository.create(follow);
		} else {
			// 更新内容の設定
			follower.setBlockFlag(true);
			follower.setDeleted(CommonConst.DeletedFlag.ON);

			// レコード更新
			ret = tFollowRepository.update(follower);
		}
		return ret;
	}

	/**
	 * プロフィールを更新する
	 *
	 * @param form
	 *            プロフィールフォーム
	 */
	@Override
	public boolean saveProfile(AccountUpdateForm form) {
		// プロフィールを更新する
		TAccount account = mapper.map(form, TAccount.class);

		TAccountExample example = new TAccountExample();
		example.createCriteria().andAccountIdEqualTo(SessionInfoContextHolder.getSessionInfo().getAccountId());
		return BooleanUtils.toBoolean(tAccountRepository.updatePartiallyBy(account, example));
	}

	/**
	 * アカウント画像を更新する
	 *
	 * @param form
	 *            画像フォーム
	 */
	public boolean saveImage(AccountImageForm form) {
		try {
			// ランダム文字列発行
			String cd = RandomStringUtils.randomAlphanumeric(10);

			// S3に保存、URLを設定する
			String fileName = SessionInfoContextHolder.getSessionInfo().getLoginId() + "/" + cd + ".png"; // TODO ファイル拡張子
			String filePath = s3Service.upload(DocumentTypeEnum.ACCOUNT, fileName, form.getUpfile());

			// プロフィールを更新する
			TAccount account = mapper.map(form, TAccount.class);
			account.setImgUrl(filePath);

			TAccountExample example = new TAccountExample();
			example.createCriteria().andAccountIdEqualTo(SessionInfoContextHolder.getSessionInfo().getAccountId());
			return BooleanUtils.toBoolean(tAccountRepository.updatePartiallyBy(account, example));

		} catch (IOException e) {
			e.printStackTrace();
			// TODO エラーメッセージ
			return false;
		}
	}

	/**
	 * FacebookIDからアカウントを取得する
	 */
	@Override
	public AccountResource findByFacebookId(String facebookId) {
		// TODO 実装

		System.out.println("facebookId: " + facebookId);
		return find("my_melody");
	}
}
