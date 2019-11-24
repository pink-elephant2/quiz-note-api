package com.api.note.quiz.service.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.note.quiz.aop.SessionInfoContextHolder;
import com.api.note.quiz.domain.TActivity;
import com.api.note.quiz.domain.TActivityExample;
import com.api.note.quiz.domain.TQuiz;
import com.api.note.quiz.enums.ActivityTypeEnum;
import com.api.note.quiz.repository.TAccountRepository;
import com.api.note.quiz.repository.TActivityRepository;
import com.api.note.quiz.repository.TQuizRepository;
import com.api.note.quiz.resources.AccountResource;
import com.api.note.quiz.resources.ActivityResource;
import com.api.note.quiz.resources.QuizResource;
import com.api.note.quiz.service.ActivityService;

/**
 * アクティビティサービス
 */
@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private TActivityRepository tActivityRepository;

	@Autowired
	private TAccountRepository tAccountRepository;

	@Autowired
	private TQuizRepository tQuizRepository;

	@Autowired
	private Mapper mapper;

	/**
	 * フォロー中のアクティビティを取得する
	 *
	 * @param pageable
	 *            ページ情報
	 * @return アクティビティ情報
	 */
	@Override
	public Page<ActivityResource> findFollowing(Pageable pageable) {
		TActivityExample example = new TActivityExample();
		// 新規投稿のみ取得する
		example.createCriteria().andAccountIdEqualTo(SessionInfoContextHolder.getSessionInfo().getAccountId())
				.andActivityTypeEqualTo(ActivityTypeEnum.NEW_POST);
		return tActivityRepository.findPageBy(example, pageable).map(tActivity -> mapResource(tActivity));
	}

	/**
	 * 自分に対するアクティビティを取得する
	 *
	 * @param pageable
	 *            ページ情報
	 * @return アクティビティ情報
	 */
	@Override
	public Page<ActivityResource> findMe(Pageable pageable) {
		TActivityExample example = new TActivityExample();
		// 新規投稿以外取得する
		example.createCriteria().andAccountIdEqualTo(SessionInfoContextHolder.getSessionInfo().getAccountId())
				.andActivityTypeNotEqualTo(ActivityTypeEnum.NEW_POST);
		return tActivityRepository.findPageBy(example, pageable).map(tActivity -> mapResource(tActivity));
	}

	/**
	 * アクティビティリソースに変換
	 *
	 * @param tActivity
	 * @return ActivityResource
	 */
	private ActivityResource mapResource(TActivity tActivity) {
		switch (tActivity.getActivityType()) {

		// フォローされた
		case FOLLOW:
			return mapResourceFollow(tActivity);

		// いいねされた
		case LIKE:
			return mapResourceLike(tActivity);

		// 投稿された
		case NEW_POST:
			return mapResourceNewPost(tActivity);

		default:
			break;
		}
		return null;
	}

	/**
	 * アクティビティリソースに変換(FOLLOW)
	 */
	private ActivityResource mapResourceFollow(TActivity tActivity) {
		ActivityResource resource = mapper.map(tActivity, ActivityResource.class);
		// アカウントを取得
		AccountResource followAccount = mapper.map(
				tAccountRepository.findOneById(tActivity.getFollowAccountId()),
				AccountResource.class);
		resource.setAccount(followAccount);
		return resource;
	}

	/**
	 * アクティビティリソースに変換(LIKE)
	 */
	private ActivityResource mapResourceLike(TActivity tActivity) {
		ActivityResource resource = mapper.map(tActivity, ActivityResource.class);
		// クイズを取得
		TQuiz quiz = tQuizRepository.findOneById(tActivity.getQuizId());
		QuizResource quizResource = mapper.map(quiz, QuizResource.class);
		resource.setQuiz(quizResource);

		// アカウントを取得
		AccountResource postAccount = mapper.map(tAccountRepository.findOneById(tActivity.getFollowAccountId()),
				AccountResource.class);
		resource.setAccount(postAccount);
		return resource;
	}

	/**
	 * アクティビティリソースに変換(NEW_POST)
	 */
	private ActivityResource mapResourceNewPost(TActivity tActivity) {
		ActivityResource resource = mapper.map(tActivity, ActivityResource.class);
		// クイズを取得
		TQuiz quiz = tQuizRepository.findOneById(tActivity.getQuizId());
		QuizResource quizResource = mapper.map(quiz, QuizResource.class);
		resource.setQuiz(quizResource);

		// アカウントを取得
		AccountResource postAccount = mapper.map(tAccountRepository.findOneById(quiz.getAccountId()),
				AccountResource.class);
		resource.setAccount(postAccount);
		return resource;
	}
}
