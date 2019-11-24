package com.api.note.quiz.service.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.note.quiz.aop.SessionInfoContextHolder;
import com.api.note.quiz.consts.CommonConst;
import com.api.note.quiz.domain.TAccount;
import com.api.note.quiz.domain.TActivity;
import com.api.note.quiz.domain.TActivityExample;
import com.api.note.quiz.domain.TFollow;
import com.api.note.quiz.domain.TFollowExample;
import com.api.note.quiz.domain.VFollow;
import com.api.note.quiz.domain.VFollowExample;
import com.api.note.quiz.enums.ActivityTypeEnum;
import com.api.note.quiz.exception.NotFoundException;
import com.api.note.quiz.repository.TAccountRepository;
import com.api.note.quiz.repository.TActivityRepository;
import com.api.note.quiz.repository.TFollowRepository;
import com.api.note.quiz.repository.VFollowRepository;
import com.api.note.quiz.resources.AccountResource;
import com.api.note.quiz.service.FollowService;

/**
 * フォローサービス
 */
@Service
@Transactional
public class FollowServiceImpl implements FollowService {

	@Autowired
	private TAccountRepository tAccountRepository;

	@Autowired
	private TFollowRepository tFollowRepository;

	@Autowired
	private VFollowRepository vFollowRepository;

	@Autowired
	private TActivityRepository tActivityRepository;

	@Autowired
	private Mapper mapper;

	/**
	 * フォローを取得する
	 *
	 * @param loginId
	 *            ログインID
	 * @param pageable
	 *            ページ情報
	 * @return アカウント情報
	 */
	@Override
	public Page<AccountResource> findFollow(String loginId, Pageable pageable) {
		// フォローリストを取得
		VFollowExample example = new VFollowExample();
		example.createCriteria().andFollowLoginIdEqualTo(loginId);
		Page<VFollow> page = vFollowRepository.findPageBy(example, pageable);

		return page.map(vfollow -> {
			AccountResource resource = mapper.map(vfollow, AccountResource.class);
			resource.setAccountId((long) vfollow.getFollowerAccountId());
			resource.setLoginId(vfollow.getFollowerLoginId());
			resource.setName(vfollow.getFollowerName());
			resource.setDescription(vfollow.getFollowerDescription());
			resource.setImgUrl(vfollow.getFollowerImgUrl());
			resource.setFollow(true);
			return resource;
		});
	}

	/**
	 * フォローワーを取得する
	 *
	 * @param loginId
	 *            ログインID
	 * @param pageable
	 *            ページ情報
	 * @return アカウント情報
	 */
	@Override
	public Page<AccountResource> findFollowers(String loginId, Pageable pageable) {
		// フォローリストを取得
		VFollowExample example = new VFollowExample();
		example.createCriteria().andFollowerLoginIdEqualTo(loginId);
		Page<VFollow> page = vFollowRepository.findPageBy(example, pageable);

		return page.map(vfollow -> {
			AccountResource resource = mapper.map(vfollow, AccountResource.class);
			resource.setAccountId((long) vfollow.getFollowAccountId());
			resource.setLoginId(vfollow.getFollowLoginId());
			resource.setName(vfollow.getFollowName());
			resource.setDescription(vfollow.getFollowDescription());
			resource.setImgUrl(vfollow.getFollowImgUrl());
			resource.setFollow(true);
			return resource;
		});
	}

	/**
	 * フォローする
	 *
	 * @param loginId
	 *            ログインID(フォロー対象)
	 */
	@Override
	public boolean follow(String loginId) throws Exception {
		// 対象ユーザの取得
		TAccount followAccount = tAccountRepository.findOneByLoginId(loginId);
		if (followAccount == null) {
			throw new NotFoundException("アカウントが存在しません");
		}

		// ログインユーザー
		Integer accountId = SessionInfoContextHolder.getSessionInfo().getAccountId();

		// フォロー済みか
		TFollowExample followExample = new TFollowExample();
		followExample.createCriteria().andAccountIdEqualTo(accountId)
				.andFollowAccountIdEqualTo(followAccount.getAccountId());
		TFollow follower = tFollowRepository.findOneBy(followExample);

		boolean ret;
		if (follower == null) {
			// 登録内容の設定
			TFollow follow = new TFollow();
			follow.setAccountId(accountId);
			follow.setFollowAccountId(followAccount.getAccountId());

			// レコード登録
			ret = tFollowRepository.create(follow);
		} else if (CommonConst.DeletedFlag.OFF.equals(follower.getDeleted())) {
			// TODO 例外
			throw new Exception("すでにフォロー済みです");
		} else {
			// レコード更新
			follower.setDeleted(CommonConst.DeletedFlag.OFF);
			ret = tFollowRepository.update(follower);
		}

		if (ret) {
			// アクティビティを登録する
			TActivityExample example = new TActivityExample();
			example.createCriteria().andAccountIdEqualTo(accountId).andActivityTypeEqualTo(ActivityTypeEnum.FOLLOW)
					.andFollowAccountIdEqualTo(followAccount.getAccountId())
					.andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
			TActivity tActivity = tActivityRepository.findOneBy(example);
			if (tActivity == null) {
				TActivity activity = new TActivity();
				activity.setAccountId(followAccount.getAccountId());
				activity.setActivityType(ActivityTypeEnum.FOLLOW);
				activity.setFollowAccountId(accountId);

				// レコード登録
				ret = tActivityRepository.create(activity);
			}
		}
		return ret;
	}

	/**
	 * フォローを解除する
	 *
	 * @param loginId
	 *            ログインID(フォロー対象)
	 */
	@Override
	public boolean unfollow(String loginId) throws Exception {
		// 対象ユーザの取得
		TAccount followAccount = tAccountRepository.findOneByLoginId(loginId);
		if (followAccount == null) {
			throw new NotFoundException("アカウントが存在しません");
		}

		// ログインユーザー
		Integer accountId = SessionInfoContextHolder.getSessionInfo().getAccountId();

		// フォロー済みか
		TFollowExample followExample = new TFollowExample();
		followExample.createCriteria().andAccountIdEqualTo(accountId)
				.andFollowAccountIdEqualTo(followAccount.getAccountId());
		TFollow follower = tFollowRepository.findOneBy(followExample);

		boolean ret;
		if (follower == null) {
			// 登録内容の設定
			TFollow follow = new TFollow();
			follow.setAccountId(accountId);
			follow.setFollowAccountId(followAccount.getAccountId());

			// レコード登録
			ret = tFollowRepository.create(follow);
		} else if (CommonConst.DeletedFlag.ON.equals(follower.getDeleted())) {
			// TODO 例外
			throw new Exception("すでにフォロー解除済みです");
		} else {
			// レコード更新
			follower.setDeleted(CommonConst.DeletedFlag.ON);
			ret = tFollowRepository.update(follower);
		}
		return ret;
	}
}
