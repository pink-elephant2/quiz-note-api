package com.api.note.quiz.domain;

import com.api.common.business.domain.AbstractBaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_group
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TGroupKey extends AbstractBaseEntity implements Serializable {
    /**
     * Database Column Remarks:
     *   グループID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_group.group_id
     *
     * @mbg.generated
     */
    private Integer groupId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_group
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_group.group_id
     *
     * @return the value of t_group.group_id
     *
     * @mbg.generated
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_group.group_id
     *
     * @param groupId the value for t_group.group_id
     *
     * @mbg.generated
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}