package com.api.note.quiz.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.note.quiz.aop.SessionInfoContextHolder;
import com.api.note.quiz.consts.CommonConst;
import com.api.note.quiz.domain.TAccountExample;
import com.api.note.quiz.domain.TActivity;
import com.api.note.quiz.domain.TActivityExample;
import com.api.note.quiz.domain.TBanReport;
import com.api.note.quiz.domain.TFollow;
import com.api.note.quiz.domain.TFollowExample;
import com.api.note.quiz.domain.TQuiz;
import com.api.note.quiz.domain.TQuizExample;
import com.api.note.quiz.domain.TQuizLike;
import com.api.note.quiz.domain.TQuizLikeExample;
import com.api.note.quiz.enums.ActivityTypeEnum;
import com.api.note.quiz.enums.ReportReasonEnum;
import com.api.note.quiz.enums.ReportTargetEnum;
import com.api.note.quiz.exception.NotFoundException;
import com.api.note.quiz.form.QuizForm;
import com.api.note.quiz.repository.TAccountRepository;
import com.api.note.quiz.repository.TActivityRepository;
import com.api.note.quiz.repository.TBanReportRepository;
import com.api.note.quiz.repository.TFollowRepository;
import com.api.note.quiz.repository.TQuizLikeRepository;
import com.api.note.quiz.repository.TQuizRepository;
import com.api.note.quiz.resources.AccountResource;
import com.api.note.quiz.resources.QuizResource;
import com.api.note.quiz.service.QuizService;
import com.api.note.quiz.service.S3Service;

/**
 * クイズサービス
 */
@Service
@Transactional
public class QuizServiceImpl implements QuizService {

	@Autowired
	private TQuizRepository tQuizRepository;

	@Autowired
	private TQuizLikeRepository tQuizLikeRepository;

	@Autowired
	private TAccountRepository tAccountRepository;

	@Autowired
	private TFollowRepository tFollowRepository;

	@Autowired
	private TActivityRepository tActivityRepository;

	@Autowired
	private TBanReportRepository tBanReportRepository;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private Mapper mapper;

	/**
	 * クイズを取得する
	 *
	 * @param cd
	 *            コード
	 * @return クイズ情報
	 */
	@Override
	public QuizResource find(String cd) {
		TQuizExample example = new TQuizExample();
		example.createCriteria().andQuizCdEqualTo(cd).andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
		TQuiz quiz = tQuizRepository.findOneBy(example);
		if (quiz == null) {
			throw new NotFoundException("クイズが存在しません");
		}

		QuizResource resource = mapper.map(quiz, QuizResource.class);

		// TODO 投稿ユーザー View または キャッシュ
		resource.setAccount(mapper.map(tAccountRepository.findOneById(quiz.getAccountId()), AccountResource.class));

		// ログインユーザー
		Integer accountId = SessionInfoContextHolder.isAuthenticated()
				? SessionInfoContextHolder.getSessionInfo().getAccountId()
				: null;

		// 自分がいいねしているか
		TQuizLikeExample likeExample = new TQuizLikeExample();
		if (SessionInfoContextHolder.isAuthenticated()) {
			likeExample.createCriteria().andQuizIdEqualTo(quiz.getQuizId()).andAccountIdEqualTo(accountId);
			TQuizLike quizLike = tQuizLikeRepository.findOneBy(likeExample);
			resource.setLike(quizLike != null && CommonConst.DeletedFlag.OFF.equals(quizLike.getDeleted()));
		}

		// いいね件数 TODO 性能改善
		likeExample.clear();
		likeExample.createCriteria().andQuizIdEqualTo(quiz.getQuizId())
				.andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
		resource.setLikeCount(tQuizLikeRepository.countBy(likeExample));

		return resource;
	}

	/**
	 * クイズ一覧を取得する
	 *
	 * @param loginId
	 *            ログインID
	 * @param pageable
	 *            ページ情報
	 * @param クイズ一覧
	 */
	@Override
	public Page<QuizResource> findList(String loginId, Pageable pageable) {
		TQuizExample example = new TQuizExample();

		// 指定されたユーザーのクイズ一覧
		AccountResource account = mapper.map(tAccountRepository.findOneByLoginId(loginId), AccountResource.class);
		example.createCriteria().andAccountIdEqualTo(account.getAccountId())
				.andDeletedEqualTo(CommonConst.DeletedFlag.OFF);

		return tQuizRepository.findPageBy(example, pageable).map(tQuiz -> {
			QuizResource resource = mapper.map(tQuiz, QuizResource.class);
			resource.setAccount(account);
			return resource;
		});
	}

	/**
	 * クイズを登録する
	 *
	 * @param form
	 *            クイズフォーム
	 * @return クイズ情報
	 */
	@Override
	public QuizResource create(QuizForm form) {
		// 新規クイズ
		String cd = RandomStringUtils.randomAlphanumeric(10);

		// レコード追加
		TQuiz quiz = mapper.map(form, TQuiz.class);
		quiz.setQuizCd(cd);
		quiz.setAccountId(SessionInfoContextHolder.getSessionInfo().getAccountId());
		tQuizRepository.create(quiz);

		// TODO コードが重複した場合、ランダム文字列を再生成してリトライする

		// フォローワーにアクティビティ登録
		createActivity(quiz.getQuizId());

		// 戻り値
		QuizResource resource = mapper.map(quiz, QuizResource.class);
		return resource;
	}

	/**
	 * フォローワーにアクティビティ登録する
	 * TODO バッチで行う
	 *
	 * @param quizId
	 */
	private void createActivity(Long quizId) {
		TFollowExample followExample = new TFollowExample();
		followExample.createCriteria()
				.andFollowAccountIdEqualTo(SessionInfoContextHolder.getSessionInfo().getAccountId())
				.andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
		List<TFollow> followList = tFollowRepository.findAllBy(followExample);
		if (!followList.isEmpty()) {
			TAccountExample accountExample = new TAccountExample();
			accountExample.createCriteria()
					.andAccountIdIn(followList.stream().map(TFollow::getAccountId).collect(Collectors.toList()));
			tAccountRepository.findAllBy(accountExample).stream().forEach(tAccount -> {
				TActivity tActivity = new TActivity();
				tActivity.setAccountId(tAccount.getAccountId());
				tActivity.setActivityType(ActivityTypeEnum.NEW_POST);
				tActivity.setQuizId(quizId);

				// レコード登録
				tActivityRepository.create(tActivity);
			});
		}
	}

	/**
	 * クイズにいいねをする/解除する
	 *
	 * @param cd
	 *            コード
	 * @param isLike
	 */
	@Override
	public boolean like(String cd, boolean isLike) {
		// クイズを取得
		TQuiz quiz = tQuizRepository.findOneByCd(cd);

		// ログインユーザ
		Integer accountId = SessionInfoContextHolder.getSessionInfo().getAccountId();

		// いいねを取得
		TQuizLikeExample likeExample = new TQuizLikeExample();
		likeExample.createCriteria().andQuizIdEqualTo(quiz.getQuizId()).andAccountIdEqualTo(accountId);
		TQuizLike quizLike = tQuizLikeRepository.findOneBy(likeExample);

		boolean ret;
		if (quizLike == null) {
			// レコード登録
			TQuizLike entity = new TQuizLike();
			entity.setQuizId(quiz.getQuizId());
			entity.setAccountId(accountId);
			entity.setDeleted(isLike ? CommonConst.DeletedFlag.OFF : CommonConst.DeletedFlag.ON);
			ret = tQuizLikeRepository.create(entity);
		} else {
			// レコード更新
			quizLike.setDeleted(isLike ? CommonConst.DeletedFlag.OFF : CommonConst.DeletedFlag.ON);
			ret = tQuizLikeRepository.updatePartially(quizLike);
		}

		if (ret && !quiz.getAccountId().equals(accountId)) {
			// アクティビティを登録する
			TActivityExample example = new TActivityExample();
			example.createCriteria().andAccountIdEqualTo(quiz.getAccountId())
					.andActivityTypeEqualTo(ActivityTypeEnum.LIKE)
					.andQuizIdEqualTo(quiz.getQuizId()).andFollowAccountIdEqualTo(accountId)
					.andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
			TActivity tActivity = tActivityRepository.findOneBy(example);
			if (tActivity == null) {
				TActivity activity = new TActivity();
				activity.setAccountId(quiz.getAccountId());
				activity.setActivityType(ActivityTypeEnum.LIKE);
				activity.setQuizId(quiz.getQuizId());
				activity.setFollowAccountId(accountId);

				// レコード登録
				ret = tActivityRepository.create(activity);
			}
		}
		return ret;
	}

	/**
	 * クイズを通報する
	 *
	 * @param cd
	 *            コード
	 * @param reason
	 *            理由
	 */
	@Override
	public boolean report(String cd, ReportReasonEnum reason) {
		// クイズを取得
		TQuiz quiz = tQuizRepository.findOneByCd(cd);

		// レコード登録
		TBanReport entity = new TBanReport();
		entity.setReportTarget(ReportTargetEnum.QUIZ);
		entity.setReason(reason);
		entity.setQuizId(quiz.getQuizId());
		entity.setReadFlag(false);
		entity.setDoneFlag(false);
		return tBanReportRepository.create(entity);
	}

	/**
	 * クイズを削除する
	 *
	 * @param cd
	 *            コード
	 */
	@Override
	public boolean remove(String cd) {
		// クイズを取得
		TQuiz quiz = tQuizRepository.findOneByCd(cd);

		// 論理削除
		TQuiz entity = new TQuiz();
		entity.setDeleted(CommonConst.DeletedFlag.ON);

		TQuizExample example = new TQuizExample();
		example.createCriteria().andQuizIdEqualTo(quiz.getQuizId())
				.andAccountIdEqualTo(SessionInfoContextHolder.getSessionInfo().getAccountId())
				.andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
		return BooleanUtils.toBoolean(tQuizRepository.updatePartiallyBy(entity, example));
	}
}