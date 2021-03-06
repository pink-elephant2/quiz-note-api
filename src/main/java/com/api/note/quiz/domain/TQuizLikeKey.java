package com.api.note.quiz.domain;

import com.api.common.business.domain.AbstractBaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_quiz_like
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TQuizLikeKey extends AbstractBaseEntity implements Serializable {
    /**
     * Database Column Remarks:
     *   クイズいいねID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_quiz_like.quiz_like_id
     *
     * @mbg.generated
     */
    private Long quizLikeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_quiz_like
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_quiz_like.quiz_like_id
     *
     * @return the value of t_quiz_like.quiz_like_id
     *
     * @mbg.generated
     */
    public Long getQuizLikeId() {
        return quizLikeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_quiz_like.quiz_like_id
     *
     * @param quizLikeId the value for t_quiz_like.quiz_like_id
     *
     * @mbg.generated
     */
    public void setQuizLikeId(Long quizLikeId) {
        this.quizLikeId = quizLikeId;
    }
}