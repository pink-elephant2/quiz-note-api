package com.api.note.quiz.service.impl;

import java.io.IOException;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.note.quiz.aop.SessionInfoContextHolder;
import com.api.note.quiz.consts.CommonConst;
import com.api.note.quiz.domain.TGroup;
import com.api.note.quiz.domain.TGroupExample;
import com.api.note.quiz.domain.TGroupMember;
import com.api.note.quiz.domain.TGroupMemberExample;
import com.api.note.quiz.enums.DocumentTypeEnum;
import com.api.note.quiz.exception.NotFoundException;
import com.api.note.quiz.form.GroupCreateForm;
import com.api.note.quiz.form.GroupImageForm;
import com.api.note.quiz.form.GroupUpdateForm;
import com.api.note.quiz.repository.TAccountRepository;
import com.api.note.quiz.repository.TGroupMemberRepository;
import com.api.note.quiz.repository.TGroupRepository;
import com.api.note.quiz.resources.AccountResource;
import com.api.note.quiz.resources.GroupResource;
import com.api.note.quiz.service.GroupService;
import com.api.note.quiz.service.S3Service;

/**
 * グループサービス
 */
//@Slf4j
@Service
@Transactional
public class GroupServiceImpl implements GroupService {

	@Autowired
	private TGroupRepository tGroupRepository;

	@Autowired
	private TGroupMemberRepository tGroupMemberRepository;

	@Autowired
	private TAccountRepository tAccountRepository;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private Mapper mapper;

	/**
	 * グループを取得する
	 *
	 * @param loginId
	 *            ログインID
	 * @param cd
	 *            グループコード
	 * @return グループ情報
	 */
	@Override
	public GroupResource find(String loginId, String cd) {
		// TODO 自分が所属するグループのみ検索する

		TGroup group = tGroupRepository.findOneByCd(cd);
		if (group == null) {
			throw new NotFoundException("グループが存在しません");
		}

		GroupResource resource = mapper.map(group, GroupResource.class);

		// TODO 投稿ユーザー View または キャッシュ
		resource.setAccount(mapper.map(tAccountRepository.findOneById(group.getAccountId()), AccountResource.class));

		return resource;
	}

	/**
	 * グループ一覧を取得する
	 *
	 * @param loginId
	 *            ログインID
	 * @param pageable
	 *            ページ情報
	 * @param グループ一覧
	 */
	@Override
	public Page<GroupResource> findList(String loginId, Pageable pageable) {
		TGroupMemberExample example = new TGroupMemberExample();

		// 指定されたユーザーのグループ一覧
		AccountResource account = mapper.map(tAccountRepository.findOneByLoginId(loginId), AccountResource.class);
		example.createCriteria().andAccountIdEqualTo(account.getAccountId())
				.andBlockedEqualTo(false) // ブロックされていない
				.andDeletedEqualTo(CommonConst.DeletedFlag.OFF);

		return tGroupMemberRepository.findPageBy(example, pageable).map(tGroupMember -> {
			// グループを取得 TODO 性能改善
			TGroup tGroup = tGroupRepository.findOneById(tGroupMember.getGroupId());

			GroupResource resource = mapper.map(tGroup, GroupResource.class);
			resource.setAccount(account);
			return resource;
		});
	}

	/**
	 * グループを登録する
	 *
	 * @param form
	 *            グループフォーム
	 * @return グループ情報
	 */
	@Override
	public GroupResource create(GroupCreateForm form) {
		// 新規グループ
		String cd = RandomStringUtils.randomAlphanumeric(10);

		// レコード追加
		TGroup group = mapper.map(form, TGroup.class);
		//		group.setGroupCd(cd); TODO グループコードカラム追加
		group.setAccountId(SessionInfoContextHolder.getSessionInfo().getAccountId());
		tGroupRepository.createReturnId(group); // groupIdがセットされる

		// TODO コードが重複した場合、ランダム文字列を再生成してリトライする

		// メンバーレコード追加
		TGroupMember groupMember = mapper.map(group, TGroupMember.class);
		groupMember.setBlocked(false);
		tGroupMemberRepository.create(groupMember);

		// 戻り値
		GroupResource resource = mapper.map(group, GroupResource.class);
		// TODO 投稿ユーザー View または キャッシュ
		resource.setAccount(mapper.map(tAccountRepository.findOneById(group.getAccountId()), AccountResource.class));
		return resource;
	}

	/**
	 * グループを更新する
	 *
	 * @param form
	 *            グループフォーム
	 * @return グループ情報
	 */
	@Override
	public GroupResource update(GroupUpdateForm form) {
		// グループを取得
		TGroup group = tGroupRepository.findOneByCd(form.getCd());

		// 更新
		TGroupExample example = new TGroupExample();
		example.createCriteria()
				.andGroupIdEqualTo(group.getGroupId())
				.andAccountIdEqualTo(SessionInfoContextHolder.getSessionInfo().getAccountId())
				.andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
		tGroupRepository.updatePartiallyBy(mapper.map(form, TGroup.class), example);

		// 戻り値
		GroupResource resource = mapper.map(form, GroupResource.class);
		// TODO 投稿ユーザー View または キャッシュ
		resource.setAccount(mapper.map(tAccountRepository.findOneById(group.getAccountId()), AccountResource.class));
		return resource;
	}

	/**
	 * グループ画像を更新する
	 *
	 * @param form
	 *            画像フォーム
	 */
	@Override
	public boolean saveImage(GroupImageForm form) {
		try {
			// グループを取得
			TGroup group = tGroupRepository.findOneByCd(form.getCd());

			// ランダム文字列発行
			String cd = RandomStringUtils.randomAlphanumeric(10);

			// S3に保存、URLを設定する TODO S3ディレクトリパス検討
			String fileName = SecurityContextHolder.getContext().getAuthentication().getName() + "/" + cd + ".png"; // TODO ファイル拡張子
			String filePath = s3Service.upload(DocumentTypeEnum.GROUP, fileName, form.getUpfile());

			// グループ画像を更新する
			group.setImgUrl(filePath);

			TGroupExample example = new TGroupExample();
			example.createCriteria()
					.andGroupIdEqualTo(group.getGroupId())
					.andAccountIdEqualTo(SessionInfoContextHolder.getSessionInfo().getAccountId())
					.andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
			return BooleanUtils.toBoolean(tGroupRepository.updatePartiallyBy(mapper.map(form, TGroup.class), example));

		} catch (IOException e) {
			e.printStackTrace();
			// TODO エラーメッセージ
			return false;
		}
	}

	/**
	 * グループを削除する
	 *
	 * @param cd
	 *            コード
	 */
	@Override
	public boolean remove(String cd) {
		// グループを取得
		TGroup group = tGroupRepository.findOneByCd(cd);

		// 論理削除
		TGroup entity = new TGroup();
		entity.setDeleted(CommonConst.DeletedFlag.ON);

		TGroupExample example = new TGroupExample();
		example.createCriteria().andGroupIdEqualTo(group.getGroupId())
				.andAccountIdEqualTo(SessionInfoContextHolder.getSessionInfo().getAccountId())
				.andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
		return BooleanUtils.toBoolean(tGroupRepository.updatePartiallyBy(entity, example));
	}
}
