package com.api.note.quiz.domain;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_quiz_like
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TQuizLike extends TQuizLikeKey implements Serializable {
    /**
     * Database Column Remarks:
     *   クイズID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_quiz_like.quiz_id
     *
     * @mbg.generated
     */
    private Long quizId;

    /**
     * Database Column Remarks:
     *   アカウントID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_quiz_like.account_id
     *
     * @mbg.generated
     */
    private Integer accountId;

    /**
     * Database Column Remarks:
     *   いいねフラグ
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_quiz_like.liked
     *
     * @mbg.generated
     */
    private Boolean liked;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_quiz_like
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_quiz_like.quiz_id
     *
     * @return the value of t_quiz_like.quiz_id
     *
     * @mbg.generated
     */
    public Long getQuizId() {
        return quizId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_quiz_like.quiz_id
     *
     * @param quizId the value for t_quiz_like.quiz_id
     *
     * @mbg.generated
     */
    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_quiz_like.account_id
     *
     * @return the value of t_quiz_like.account_id
     *
     * @mbg.generated
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_quiz_like.account_id
     *
     * @param accountId the value for t_quiz_like.account_id
     *
     * @mbg.generated
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_quiz_like.liked
     *
     * @return the value of t_quiz_like.liked
     *
     * @mbg.generated
     */
    public Boolean getLiked() {
        return liked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_quiz_like.liked
     *
     * @param liked the value for t_quiz_like.liked
     *
     * @mbg.generated
     */
    public void setLiked(Boolean liked) {
        this.liked = liked;
    }
}