package com.api.note.quiz.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.note.quiz.resources.ActivityResource;
import com.api.note.quiz.service.ActivityService;

/**
 * (認証必須)アクティビティAPI TODO インターセプタで自分のログインIDかチェックする
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/user/{loginId}/activity")
public class UserActivityController {

	@Autowired
	private ActivityService activityService;

	/**
	 * フォロー中のアクティビティを取得する
	 */
	@GetMapping("/following")
	@ResponseStatus(HttpStatus.OK)
	public Page<ActivityResource> getFollowing(@SortDefault.SortDefaults({
			@SortDefault(sort = "activity_id", direction = Direction.DESC) }) Pageable pageable) {
		// アクティビティを取得する
		return activityService.findFollowing(pageable);
	}

	/**
	 * 自分に対するアクティビティを取得する
	 */
	@GetMapping("/me")
	@ResponseStatus(HttpStatus.OK)
	public Page<ActivityResource> getMe(@SortDefault.SortDefaults({
			@SortDefault(sort = "activity_id", direction = Direction.DESC) }) Pageable pageable) {
		// アクティビティを取得する
		return activityService.findMe(pageable);
	}
}
