package com.api.note.quiz.repository;

import org.springframework.context.annotation.Primary;
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
}