package org.zahid.apps.web.pos.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * The persistent class for the XXIM_EXPENSE_DTL database table.
 * 
 */
@Entity
@Table(name = "XXIM_EXPENSE_DTL")
@NamedQuery(name = "ExpenseDtl.findAll", query = "SELECT e FROM ExpenseDtl e")
public class ExpenseDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "XXIM_EXPENSE_DTL_EXPDTLID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXIM_EXPENSE_DTL_EXPDTLID_GENERATOR")
	@Column(name = "EXP_DTL_ID")
	private Long expDtlId;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;

	@Column(name = "EXP_DTL_AMT")
	private BigDecimal expDtlAmt;

	@Column(name = "EXP_OTHER_TYPE_DESC")
	private String expOtherTypeDesc;

	@Column(name = "EXP_TYPE")
	private String expType;

	@Column(name = "LAST_UPDATE_DATE")
	private Timestamp lastUpdateDate;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	private String remarks;

	// bi-directional many-to-one association to ExpenseMain
	@ManyToOne
	@JoinColumn(name = "EXP_MAIN_ID")
	private ExpenseMain expenseMain;

	public ExpenseDtl() {
	}

	public Long getExpDtlId() {
		return expDtlId;
	}

	public void setExpDtlId(Long expDtlId) {
		this.expDtlId = expDtlId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public BigDecimal getExpDtlAmt() {
		return expDtlAmt;
	}

	public void setExpDtlAmt(BigDecimal expDtlAmt) {
		this.expDtlAmt = expDtlAmt;
	}

	public String getExpOtherTypeDesc() {
		return expOtherTypeDesc;
	}

	public void setExpOtherTypeDesc(String expOtherTypeDesc) {
		this.expOtherTypeDesc = expOtherTypeDesc;
	}

	public String getExpType() {
		return expType;
	}

	public void setExpType(String expType) {
		this.expType = expType;
	}

	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ExpenseMain getExpenseMain() {
		return expenseMain;
	}

	public void setExpenseMain(ExpenseMain expenseMain) {
		this.expenseMain = expenseMain;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (expDtlId != null ? expDtlId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ExpenseDtl)) {
			return false;
		}
		ExpenseDtl other = (ExpenseDtl) object;
		if ((this.expDtlId == null && other.expDtlId != null) || (this.expDtlId != null && !this.expDtlId.equals(other.expDtlId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "org.zahid.apps.web.pos.entity.ExpenseDtl[ expDtlId=" + expDtlId + " ]";
	}
}