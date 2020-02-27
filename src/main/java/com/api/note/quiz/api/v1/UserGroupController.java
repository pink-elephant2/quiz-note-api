package com.api.note.quiz.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.note.quiz.form.GroupCreateForm;
import com.api.note.quiz.form.GroupImageForm;
import com.api.note.quiz.form.GroupMemberCreateForm;
import com.api.note.quiz.form.GroupUpdateForm;
import com.api.note.quiz.resources.GroupMemberResource;
import com.api.note.quiz.resources.GroupResource;
import com.api.note.quiz.service.GroupService;

/**
 * (認証必須)グループAPI TODO インターセプタで自分のログインIDかチェックする
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/user/{loginId}/group")
public class UserGroupController {

	@Autowired
	private GroupService groupService;

	/**
	 * グループ取得
	 *
	 * @param cd
	 *            コード
	 */
	@GetMapping("/{cd}")
	@ResponseStatus(HttpStatus.OK)
	public GroupResource find(@PathVariable("cd") String cd) {
		// 自分が所属するグループを取得する
		return groupService.find(SecurityContextHolder.getContext().getAuthentication().getName(), cd);
	}

	/**
	 * グループ一覧取得
	 *
	 * @param loginId
	 *            ログインID
	 * @param pageable
	 *            ページ情報
	 */
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<GroupResource> findList(@SortDefault.SortDefaults({
			@SortDefault(sort = "updated_at", direction = Direction.DESC) }) Pageable pageable) {
		// 自分が所属するグループ一覧を取得する
		return groupService.findList(SecurityContextHolder.getContext().getAuthentication().getName(), pageable);
	}

	/**
	 * グループ登録
	 *
	 * @param form
	 *            グループフォーム
	 * @return グループ情報
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GroupResource create(@RequestBody @Validated GroupCreateForm form) {
		// グループを登録し、登録内容を返却する
		return groupService.create(form);
	}

	/**
	 * グループ更新
	 *
	 * @param form
	 *            グループフォーム
	 * @return グループ情報
	 */
	@PutMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GroupResource update(@RequestBody @Validated GroupUpdateForm form) {
		// グループを更新し、登録内容を返却する
		return groupService.update(form);
	}

	/**
	 * グループ画像登録
	 *
	 * @param form
	 *            グループフォーム
	 * @return グループ情報
	 */
	@PostMapping("/{cd}/image")
	@ResponseStatus(HttpStatus.CREATED)
	public boolean updateImage(@PathVariable("cd") String cd, @Validated GroupImageForm form) {
		// グループを更新し、登録内容を返却する
		return groupService.saveImage(form);
	}

	/**
	 * グループを削除する
	 *
	 * @param cd
	 *            コード
	 */
	@DeleteMapping("/{cd}")
	@ResponseStatus(HttpStatus.CREATED)
	public boolean remove(@PathVariable("cd") String cd) {
		// グループを削除する
		return groupService.remove(cd);
	}

	/**
	 * グループメンバー一覧取得
	 *
	 * @param cd
	 *            コード
	 * @param pageable
	 *            ページ情報
	 */
	@GetMapping("/{cd}/member")
	@ResponseStatus(HttpStatus.OK)
	public Page<GroupMemberResource> findMemberList(@PathVariable("cd") String cd, @SortDefault.SortDefaults({
			@SortDefault(sort = "updated_at", direction = Direction.DESC) }) Pageable pageable) {
		// 自分が所属するグループのメンバー一覧を取得する
		return groupService.findMemberList(SecurityContextHolder.getContext().getAuthentication().getName(), cd,
				pageable);
	}

	/**
	 * グループメンバー登録
	 *
	 * @param form グループメンバーフォーム
	 */
	@PostMapping("/{cd}/member")
	@ResponseStatus(HttpStatus.CREATED)
	public boolean createMember(@PathVariable("cd") String cd, @RequestBody @Validated GroupMemberCreateForm form) {
		// グループメンバーを登録する
		return groupService.createMember(SecurityContextHolder.getContext().getAuthentication().getName(), form);
	}

	/**
	 * グループメンバー削除
	 *
	 * @param cd コード
	 * @param memberLoginId 削除対象のログインID
	 */
	@DeleteMapping("/{cd}/member")
	@ResponseStatus(HttpStatus.CREATED)
	public boolean removeMember(@PathVariable("cd") String cd, String memberLoginId) {
		// グループメンバーを削除する
		return groupService.removeMember(cd, SecurityContextHolder.getContext().getAuthentication().getName(),
				memberLoginId);
	}

	/**
	 * グループメンバー管理者更新
	 *
	 * @param cd コード
	 * @param managerLoginId 管理者にするログインID
	 */
	@PutMapping("/{cd}/manager/{managerLoginId}")
	@ResponseStatus(HttpStatus.CREATED)
	public boolean updateManager(@PathVariable String cd, @PathVariable("managerLoginId") String managerLoginId) {
		// グループの管理者を更新する
		return groupService.updateManager(cd, SecurityContextHolder.getContext().getAuthentication().getName(),
				managerLoginId);
	}

	/**
	 * おすすめグループ一覧取得
	 *
	 * @param loginId
	 *            ログインID
	 * @param pageable
	 *            ページ情報
	 */
	@GetMapping("/recommend")
	@ResponseStatus(HttpStatus.OK)
	public Page<GroupResource> findRecommendList(@SortDefault.SortDefaults({
			@SortDefault(sort = "updated_at", direction = Direction.DESC) }) Pageable pageable) {
		// TODO 実装
		// 自分が所属するグループ一覧を取得する
		return groupService.findRecommendList(SecurityContextHolder.getContext().getAuthentication().getName(), pageable);
	}

}
