package com.api.note.quiz.domain;

import com.api.common.business.domain.AbstractBaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_ban_report
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TBanReportKey extends AbstractBaseEntity implements Serializable {
    /**
     * Database Column Remarks:
     *   通報ID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ban_report.report_id
     *
     * @mbg.generated
     */
    private Integer reportId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_ban_report
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ban_report.report_id
     *
     * @return the value of t_ban_report.report_id
     *
     * @mbg.generated
     */
    public Integer getReportId() {
        return reportId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ban_report.report_id
     *
     * @param reportId the value for t_ban_report.report_id
     *
     * @mbg.generated
     */
    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }
}