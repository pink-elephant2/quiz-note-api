package com.api.note.quiz.repository;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.api.note.quiz.consts.CommonConst;
import com.api.note.quiz.domain.TGroup;
import com.api.note.quiz.domain.TGroupExample;
import com.api.note.quiz.exception.NotFoundException;

@Primary
@Repository
public class TGroupRepositoryImpl implements TGroupRepository {
	private TGroupMapper tGroupMapper;

	public TGroupRepositoryImpl(TGroupMapper tGroupMapper) {
		this.tGroupMapper = tGroupMapper;
	}

	@Override
	public TGroupMapper getMapper() {
		return this.tGroupMapper;
	}

	/**
	 * グループIDからレコードを取得する
	 *
	 * @param groupId グループID
	 */
	@Override
	public TGroup findOneById(Integer groupId) {
		TGroupExample groupExample = new TGroupExample();
		groupExample.createCriteria().andGroupIdEqualTo(groupId).andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
		TGroup group = findOneBy(groupExample);
		if (group == null) {
			throw new NotFoundException("グループが存在しません");
		}
		return group;
	}

	/**
	 * グループCDからレコードを取得する
	 *
	 * @param groupCd グループCD
	 */
	@Override
	public TGroup findOneByCd(String groupCd) {
		TGroupExample groupExample = new TGroupExample();
		groupExample.createCriteria().andGroupCdEqualTo(groupCd).andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
		TGroup group = findOneBy(groupExample);
		if (group == null) {
			throw new NotFoundException("グループが存在しません");
		}
		return group;
	}

	/**
	 * レコードを登録してIDを返却する
	 */
	@Override
	public Integer createReturnId(TGroup tGroup) {
		this.beforeInsert(tGroup);
		return tGroupMapper.insertReturnId(tGroup);
	}

	/**
	 * 所属していないグループを取得する
	 */
	@Override
	public Page<TGroup> findPageWithout(Integer accountId, Pageable pageable) {
		long total = this.getMapper().countByWithout(accountId);

		System.out.println(total);

		if (total == 0L) {
			return new PageImpl<TGroup>(Collections.emptyList(), pageable, total);

		} else {
			Integer limit = Integer.valueOf(pageable.getPageSize());
			int offset = (int) (pageable.getOffset());

			System.out.println(limit);
			System.out.println(offset);
			List<TGroup> pageContent1 = this.getMapper().selectByWithout(accountId, limit, offset);

			return new PageImpl<TGroup>(pageContent1, pageable, total);
		}
	}
}