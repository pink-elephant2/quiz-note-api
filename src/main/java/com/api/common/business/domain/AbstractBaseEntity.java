package com.api.common.business.domain;

import java.beans.Transient;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class AbstractBaseEntity {
	private String deleted;
	private Date createdAt;
	private Date updatedAt;
	private String updatedBy;
	private String createdBy;
	@JsonIgnore
	private boolean optimisticLockEnabled;
	@JsonIgnore
	private boolean internalAutoSetUserIdEnabled = true;

	public String toString() {
		return "AbstractBaseEntity(deleted=" + this.getDeleted() + "createdAt=" + this.getCreatedAt() + ", updatedAt="
				+ this.getUpdatedAt() + ", updatedBy=" + this.getUpdatedBy() + ", createdBy=" + this.getCreatedBy()
				+ ", optimisticLockEnabled=" + this.isOptimisticLockEnabled() + ", internalAutoSetUserIdEnabled="
				+ this.isInternalAutoSetUserIdEnabled() + ")";
	}

	public String getDeleted() {
		return this.deleted;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public boolean isInternalAutoSetUserIdEnabled() {
		return this.internalAutoSetUserIdEnabled;
	}

	@Transient
	public boolean isOptimisticLockEnabled() {
		return this.optimisticLockEnabled;
	}

	public void enableOptimisticLock() {
		this.optimisticLockEnabled = true;
	}

	public void disableOptimisticLock() {
		this.optimisticLockEnabled = false;
	}

	@Transient
	public boolean canAutoSetUserId() {
		return this.internalAutoSetUserIdEnabled;
	}

	public void enableAutoSetUserId() {
		this.internalAutoSetUserIdEnabled = true;
	}

	public void disableAutoSetUserId() {
		this.internalAutoSetUserIdEnabled = false;
	}
}
