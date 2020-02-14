package com.api.note.quiz.resources;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * グループAPIレスポンス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupResource {

    /** グループ名 */
    private String name;

    /** 管理者アカウント */
    private AccountResource account;

    /** 公式フラグ */
    private Boolean official;

    /** 自己紹介 */
    private String description;

    /** 画像URL */
    private String imgUrl;

}
