package org.zahid.apps.web.pos.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the XXIM_EXPENSE_MAIN database table.
 */
@Entity
@Table(name = "XXIM_EXPENSE_MAIN")
@NamedQuery(name = "ExpenseMain.findAll", query = "SELECT e FROM ExpenseMain e")
public class ExpenseMain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "XXIM_EXPENSE_MAIN_EXPMAINID_GENERATOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXIM_EXPENSE_MAIN_EXPMAINID_GENERATOR")
    @Column(name = "EXP_MAIN_ID")
    private Long expMainId;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "EXP_DATE")
    private Date expDate;

    @Column(name = "LAST_UPDATE_DATE")
    private Timestamp lastUpdateDate;

    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

    private String remarks;

    // bi-directional many-to-one association to ExpenseDtl
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "expenseMain")
    private List<ExpenseDtl> expenseDtls;

    public ExpenseMain() {
    }

    public Long getExpMainId() {
        return expMainId;
    }

    public void setExpMainId(Long expMainId) {
        this.expMainId = expMainId;
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

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
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

    public List<ExpenseDtl> getExpenseDtls() {
        return expenseDtls;
    }

    public void setExpenseDtls(List<ExpenseDtl> expenseDtls) {
        this.expenseDtls = expenseDtls;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (expMainId != null ? expMainId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ExpenseMain)) {
            return false;
        }
        ExpenseMain other = (ExpenseMain) object;
        if ((this.expMainId == null && other.expMainId != null) || (this.expMainId != null && !this.expMainId.equals(other.expMainId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.zahid.apps.web.pos.entity.ExpenseMain[ expMainId=" + expMainId + " ]";
    }

}