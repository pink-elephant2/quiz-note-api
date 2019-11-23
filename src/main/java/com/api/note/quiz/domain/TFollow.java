package com.api.note.quiz.domain;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_follow
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TFollow extends TFollowKey implements Serializable {
    /**
     * Database Column Remarks:
     *   アカウントID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_follow.account_id
     *
     * @mbg.generated
     */
    private Integer accountId;

    /**
     * Database Column Remarks:
     *   フォローアカウントID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_follow.follow_account_id
     *
     * @mbg.generated
     */
    private Integer followAccountId;

    /**
     * Database Column Remarks:
     *   ブロックフラグ
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_follow.block_flag
     *
     * @mbg.generated
     */
    private Boolean blockFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_follow
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_follow.account_id
     *
     * @return the value of t_follow.account_id
     *
     * @mbg.generated
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_follow.account_id
     *
     * @param accountId the value for t_follow.account_id
     *
     * @mbg.generated
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_follow.follow_account_id
     *
     * @return the value of t_follow.follow_account_id
     *
     * @mbg.generated
     */
    public Integer getFollowAccountId() {
        return followAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_follow.follow_account_id
     *
     * @param followAccountId the value for t_follow.follow_account_id
     *
     * @mbg.generated
     */
    public void setFollowAccountId(Integer followAccountId) {
        this.followAccountId = followAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_follow.block_flag
     *
     * @return the value of t_follow.block_flag
     *
     * @mbg.generated
     */
    public Boolean getBlockFlag() {
        return blockFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_follow.block_flag
     *
     * @param blockFlag the value for t_follow.block_flag
     *
     * @mbg.generated
     */
    public void setBlockFlag(Boolean blockFlag) {
        this.blockFlag = blockFlag;
    }
}