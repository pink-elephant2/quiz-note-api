package com.api.note.quiz.domain;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_group
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TGroup extends TGroupKey implements Serializable {
    /**
     * Database Column Remarks:
     *   グループCD
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_group.group_cd
     *
     * @mbg.generated
     */
    private String groupCd;

    /**
     * Database Column Remarks:
     *   グループ名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_group.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     * Database Column Remarks:
     *   管理者アカウントID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_group.account_id
     *
     * @mbg.generated
     */
    private Integer accountId;

    /**
     * Database Column Remarks:
     *   公式フラグ
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_group.official
     *
     * @mbg.generated
     */
    private Boolean official;

    /**
     * Database Column Remarks:
     *   自己紹介
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_group.description
     *
     * @mbg.generated
     */
    private String description;

    /**
     * Database Column Remarks:
     *   画像URL
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_group.img_url
     *
     * @mbg.generated
     */
    private String imgUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_group
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_group.group_cd
     *
     * @return the value of t_group.group_cd
     *
     * @mbg.generated
     */
    public String getGroupCd() {
        return groupCd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_group.group_cd
     *
     * @param groupCd the value for t_group.group_cd
     *
     * @mbg.generated
     */
    public void setGroupCd(String groupCd) {
        this.groupCd = groupCd == null ? null : groupCd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_group.name
     *
     * @return the value of t_group.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_group.name
     *
     * @param name the value for t_group.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_group.account_id
     *
     * @return the value of t_group.account_id
     *
     * @mbg.generated
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_group.account_id
     *
     * @param accountId the value for t_group.account_id
     *
     * @mbg.generated
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_group.official
     *
     * @return the value of t_group.official
     *
     * @mbg.generated
     */
    public Boolean getOfficial() {
        return official;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_group.official
     *
     * @param official the value for t_group.official
     *
     * @mbg.generated
     */
    public void setOfficial(Boolean official) {
        this.official = official;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_group.description
     *
     * @return the value of t_group.description
     *
     * @mbg.generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_group.description
     *
     * @param description the value for t_group.description
     *
     * @mbg.generated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_group.img_url
     *
     * @return the value of t_group.img_url
     *
     * @mbg.generated
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_group.img_url
     *
     * @param imgUrl the value for t_group.img_url
     *
     * @mbg.generated
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }
}