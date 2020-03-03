package com.api.note.quiz.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.note.quiz.aop.SessionInfoContextHolder;
import com.api.note.quiz.consts.CommonConst;
import com.api.note.quiz.domain.TAccount;
import com.api.note.quiz.domain.TGroup;
import com.api.note.quiz.domain.TGroupExample;
import com.api.note.quiz.domain.TGroupMember;
import com.api.note.quiz.domain.TGroupMemberExample;
import com.api.note.quiz.enums.DocumentTypeEnum;
import com.api.note.quiz.exception.NotFoundException;
import com.api.note.quiz.form.GroupCreateForm;
import com.api.note.quiz.form.GroupImageForm;
import com.api.note.quiz.form.GroupMemberCreateForm;
import com.api.note.quiz.form.GroupUpdateForm;
import com.api.note.quiz.repository.TAccountRepository;
import com.api.note.quiz.repository.TGroupMemberRepository;
import com.api.note.quiz.repository.TGroupRepository;
import com.api.note.quiz.resources.AccountResource;
import com.api.note.quiz.resources.GroupMemberResource;
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
		TGroup group = tGroupRepository.findOneByCd(cd);
		if (group == null) {
			throw new NotFoundException("グループが存在しません");
		}

		GroupResource resource = mapper.map(group, GroupResource.class);

		// TODO 投稿ユーザー View または キャッシュ
		TAccount account = tAccountRepository.findOneById(group.getAccountId());
		resource.setAccount(mapper.map(account, AccountResource.class));

		// ログイン済みの場合
		if (!(SecurityContextHolder.getContext() == null
				|| SecurityContextHolder.getContext().getAuthentication() == null
				|| SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
				&& SecurityContextHolder.getContext().getAuthentication().getName().equals(loginId)) {

			// グループに所属しているか
			TGroupMemberExample example = new TGroupMemberExample();
			example.createCriteria().andGroupIdEqualTo(group.getGroupId())
					.andAccountIdEqualTo(tAccountRepository.findOneByLoginId(loginId).getAccountId())
					.andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
			TGroupMember groupMember = tGroupMemberRepository.findOneBy(example);

			if (groupMember != null) {
				resource.setMember(BooleanUtils.isNotTrue(groupMember.getBlocked()));
				resource.setBlocked(BooleanUtils.isTrue(groupMember.getBlocked()));
			}
		}
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
		group.setGroupCd(cd);
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
		resource.setGroupCd(form.getCd());
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

			// S3に保存、URLを設定する
			String fileName = form.getCd() + "/" + cd + ".png"; // TODO ファイル拡張子
			String filePath = s3Service.upload(DocumentTypeEnum.GROUP, fileName, form.getUpfile());

			// グループ画像を更新する
			group.setImgUrl(filePath);

			TGroupExample example = new TGroupExample();
			example.createCriteria()
					.andGroupIdEqualTo(group.getGroupId())
					.andAccountIdEqualTo(SessionInfoContextHolder.getSessionInfo().getAccountId())
					.andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
			return BooleanUtils.toBoolean(tGroupRepository.updatePartiallyBy(group, example));

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

	/**
	 * メンバー一覧を取得する
	 *
	 * @param loginId
	 *            ログインID TODO 自分が所属するグループに絞るか仕様検討
	 * @param cd
	 *            コード
	 * @param pageable
	 *            ページ情報
	 * @param メンバー一覧
	 */
	@Override
	public Page<GroupMemberResource> findMemberList(String loginId, String cd, Pageable pageable) {
		// グループを取得
		TGroup group = tGroupRepository.findOneByCd(cd);

		// グループメンバーを取得
		TGroupMemberExample example = new TGroupMemberExample();
		example.createCriteria().andGroupIdEqualTo(group.getGroupId())
				.andBlockedEqualTo(false) // ブロックされていない
				.andDeletedEqualTo(CommonConst.DeletedFlag.OFF);

		return tGroupMemberRepository.findPageBy(example, pageable).map(tGroupMember -> {
			GroupMemberResource resource = mapper.map(tGroupMember, GroupMemberResource.class);

			// アカウントを取得 TODO 性能改善
			TAccount tAccount = tAccountRepository.findOneById(tGroupMember.getAccountId());

			resource.setAccount(mapper.map(tAccount, AccountResource.class));
			resource.setManager(group.getAccountId().equals(tAccount.getAccountId()));
			return resource;
		});
	}

	/**
	 * メンバー一覧を取得する
	 *
	 * @param loginId
	 *            ログインID TODO 自分が所属するグループに絞るか仕様検討
	 * @param cd
	 *            コード
	 * @param メンバー一覧
	 */
	@Override
	public List<TGroupMember> findMemberListAll(String loginId, String cd) {
		// グループを取得
		TGroup group = tGroupRepository.findOneByCd(cd);

		// グループメンバーを取得
		TGroupMemberExample example = new TGroupMemberExample();
		example.createCriteria().andGroupIdEqualTo(group.getGroupId())
				.andBlockedEqualTo(false) // ブロックされていない
				.andDeletedEqualTo(CommonConst.DeletedFlag.OFF);

		return tGroupMemberRepository.findAllBy(example);
	}

	/**
	 * メンバーを登録する
	 *
	 * @param cd コード
	 * @param loginId ログインID
	 * @param form グループメンバーフォーム
	 */
	@Override
	public boolean createMember(String loginId, GroupMemberCreateForm form) {
		// グループを取得
		GroupResource group = find(loginId, form.getCd());

		// アカウントを取得
		TAccount account = tAccountRepository.findOneByLoginId(form.getMemberLoginId());
		if (account == null) {
			throw new NotFoundException("アカウントが存在しません");
		}

		// グループメンバーを取得
		TGroupMemberExample example = new TGroupMemberExample();
		example.createCriteria().andGroupIdEqualTo(group.getGroupId())
				.andAccountIdEqualTo(account.getAccountId())
				.andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
		TGroupMember groupMember = tGroupMemberRepository.findOneBy(example);

		if (groupMember != null) {
			// TODO 403エラー 既に登録済み
			return false;
		}

		// メンバーレコード追加
		groupMember = mapper.map(group, TGroupMember.class);
		groupMember.setAccountId(account.getAccountId());
		groupMember.setBlocked(false);
		// TODO 招待中フラグ追加
		return tGroupMemberRepository.create(groupMember);
	}

	/**
	 * メンバーを更新する
	 */
	// TODO 実装

	/**
	 * メンバーを削除する
	 *
	 * @param cd コード
	 * @param loginId ログインID
	 * @param memberLoginId 削除対象のログインID
	 */
	@Override
	public boolean removeMember(String cd, String loginId, String memberLoginId) {
		// グループを取得
		GroupResource group = find(loginId, cd);

		// 管理者ではない場合、自分以外は削除できない
		if (!(SessionInfoContextHolder.getSessionInfo().getAccountId().equals(group.getAccount().getAccountId())
				|| loginId.equals(memberLoginId))) {
			// TODO 403エラー
			return false;
		}

		// アカウントを取得
		TAccount account = tAccountRepository.findOneByLoginId(memberLoginId);

		// グループメンバーを取得
		TGroupMemberExample example = new TGroupMemberExample();
		example.createCriteria().andGroupIdEqualTo(group.getGroupId())
				.andAccountIdEqualTo(account.getAccountId())
				.andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
		TGroupMember groupMember = tGroupMemberRepository.findOneBy(example);

		if (groupMember == null) {
			throw new NotFoundException("メンバーが存在しません");
		}

		// グループメンバー削除
		groupMember.setDeleted(CommonConst.DeletedFlag.ON);
		boolean ret = tGroupMemberRepository.updatePartially(groupMember);

		// 残りのメンバーを取得
		Page<GroupMemberResource> groupMemberList = findMemberList(loginId, cd,
				PageRequest.of(0, 1, Sort.by(Order.asc("created_at"))));
		if (0 == groupMemberList.getTotalElements()) {
			// メンバーがいなくなった場合、グループも削除する
			ret = remove(cd);
		} else if (1 == groupMemberList.getTotalElements() && !groupMemberList.getContent().get(0).isManager()) {
			// 最後の1名になった場合、その人を管理者にする
			TGroup tGroup = mapper.map(group, TGroup.class);
			tGroup.setAccountId(groupMemberList.getContent().get(0).getAccount().getAccountId()); // 管理者アカウントID
			ret = tGroupRepository.updatePartially(tGroup);
		}

		return ret;
	}

	/**
	 * グループの管理者を更新する
	 *
	 * @param cd コード
	 * @param loginId ログインID
	 * @param managerLoginId 管理者にするログインID
	 */
	public boolean updateManager(String cd, String loginId, String managerLoginId) {
		// グループを取得
		GroupResource group = find(loginId, cd);

		// 管理者ではない場合、更新できない
		if (!SessionInfoContextHolder.getSessionInfo().getAccountId().equals(group.getAccount().getAccountId())
				|| loginId.equals(managerLoginId)) {
			// TODO 403エラー
			return false;
		}

		// アカウントを取得
		TAccount account = tAccountRepository.findOneByLoginId(managerLoginId);

		// グループメンバーを取得
		TGroupMemberExample example = new TGroupMemberExample();
		example.createCriteria().andGroupIdEqualTo(group.getGroupId())
				.andAccountIdEqualTo(account.getAccountId())
				.andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
		TGroupMember groupMember = tGroupMemberRepository.findOneBy(example);

		if (groupMember == null) {
			throw new NotFoundException("メンバーが存在しません");
		}

		// グループ更新
		TGroup tGroup = mapper.map(group, TGroup.class);
		tGroup.setAccountId(account.getAccountId()); // 管理者アカウントID
		return tGroupRepository.updatePartially(tGroup);
	}

	/**
	 * おすすめグループ一覧を取得する
	 *
	 * @param loginId
	 *            ログインID
	 * @param pageable
	 *            ページ情報
	 * @param グループ一覧
	 */
	public Page<GroupResource> findRecommendList(String loginId, Pageable pageable) {
		AccountResource account = mapper.map(tAccountRepository.findOneByLoginId(loginId), AccountResource.class);

		// 指定されたユーザーが所属していないグループ一覧
		return tGroupRepository.findPageWithout(account.getAccountId(), pageable).map(tGroup -> {
			GroupResource resource = mapper.map(tGroup, GroupResource.class);
			resource.setAccount(account);
			return resource;
		});
	}
}
