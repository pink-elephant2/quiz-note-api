package com.api.note.quiz.domain;

import com.api.note.quiz.enums.ReportReasonEnum;
import com.api.note.quiz.enums.ReportTargetEnum;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_ban_report
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TBanReport extends TBanReportKey implements Serializable {
    /**
     * Database Column Remarks:
     *   通報対象
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ban_report.report_target
     *
     * @mbg.generated
     */
    private ReportTargetEnum reportTarget;

    /**
     * Database Column Remarks:
     *   理由
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ban_report.reason
     *
     * @mbg.generated
     */
    private ReportReasonEnum reason;

    /**
     * Database Column Remarks:
     *   アカウントID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ban_report.account_id
     *
     * @mbg.generated
     */
    private Integer accountId;

    /**
     * Database Column Remarks:
     *   クイズID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ban_report.quiz_id
     *
     * @mbg.generated
     */
    private Long quizId;

    /**
     * Database Column Remarks:
     *   既読フラグ
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ban_report.read_flag
     *
     * @mbg.generated
     */
    private Boolean readFlag;

    /**
     * Database Column Remarks:
     *   処理済みフラグ
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ban_report.done_flag
     *
     * @mbg.generated
     */
    private Boolean doneFlag;

    /**
     * Database Column Remarks:
     *   メモ
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ban_report.memo
     *
     * @mbg.generated
     */
    private String memo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_ban_report
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ban_report.report_target
     *
     * @return the value of t_ban_report.report_target
     *
     * @mbg.generated
     */
    public ReportTargetEnum getReportTarget() {
        return reportTarget;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ban_report.report_target
     *
     * @param reportTarget the value for t_ban_report.report_target
     *
     * @mbg.generated
     */
    public void setReportTarget(ReportTargetEnum reportTarget) {
        this.reportTarget = reportTarget;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ban_report.reason
     *
     * @return the value of t_ban_report.reason
     *
     * @mbg.generated
     */
    public ReportReasonEnum getReason() {
        return reason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ban_report.reason
     *
     * @param reason the value for t_ban_report.reason
     *
     * @mbg.generated
     */
    public void setReason(ReportReasonEnum reason) {
        this.reason = reason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ban_report.account_id
     *
     * @return the value of t_ban_report.account_id
     *
     * @mbg.generated
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ban_report.account_id
     *
     * @param accountId the value for t_ban_report.account_id
     *
     * @mbg.generated
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ban_report.quiz_id
     *
     * @return the value of t_ban_report.quiz_id
     *
     * @mbg.generated
     */
    public Long getQuizId() {
        return quizId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ban_report.quiz_id
     *
     * @param quizId the value for t_ban_report.quiz_id
     *
     * @mbg.generated
     */
    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ban_report.read_flag
     *
     * @return the value of t_ban_report.read_flag
     *
     * @mbg.generated
     */
    public Boolean getReadFlag() {
        return readFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ban_report.read_flag
     *
     * @param readFlag the value for t_ban_report.read_flag
     *
     * @mbg.generated
     */
    public void setReadFlag(Boolean readFlag) {
        this.readFlag = readFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ban_report.done_flag
     *
     * @return the value of t_ban_report.done_flag
     *
     * @mbg.generated
     */
    public Boolean getDoneFlag() {
        return doneFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ban_report.done_flag
     *
     * @param doneFlag the value for t_ban_report.done_flag
     *
     * @mbg.generated
     */
    public void setDoneFlag(Boolean doneFlag) {
        this.doneFlag = doneFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ban_report.memo
     *
     * @return the value of t_ban_report.memo
     *
     * @mbg.generated
     */
    public String getMemo() {
        return memo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ban_report.memo
     *
     * @param memo the value for t_ban_report.memo
     *
     * @mbg.generated
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}