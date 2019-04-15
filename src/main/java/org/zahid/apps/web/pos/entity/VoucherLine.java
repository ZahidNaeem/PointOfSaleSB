package org.zahid.apps.web.pos.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the XXIM_VOUCHER_LINE database table.
 */
@Entity
@Table(name = "XXIM_VOUCHER_LINE")
@NamedQuery(name = "VoucherLine.findAll", query = "SELECT v FROM VoucherLine v")
public class VoucherLine implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "XXIM_VOUCHER_LINE_LINEID_GENERATOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXIM_VOUCHER_LINE_LINEID_GENERATOR")
    @Column(name = "LINE_ID")
    private Long lineId;

    private BigDecimal amount;

    @Column(name = "AMOUNT_MODE")
    private String amountMode;

    @Temporal(TemporalType.DATE)
    @Column(name = "CHEQUE_DATE")
    private Date chequeDate;

    @Column(name = "CHEQUE_NUM")
    private String chequeNum;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;

    @Column(name = "LAST_UPDATE_DATE")
    private Timestamp lastUpdateDate;

    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

    private String remarks;

    // bi-directional many-to-one association to VoucherHeader
    @ManyToOne
    @JoinColumn(name = "HEADER_ID")
    private VoucherHeader voucherHeader;

    public VoucherLine() {
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAmountMode() {
        return amountMode;
    }

    public void setAmountMode(String amountMode) {
        this.amountMode = amountMode;
    }

    public Date getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(Date chequeDate) {
        this.chequeDate = chequeDate;
    }

    public String getChequeNum() {
        return chequeNum;
    }

    public void setChequeNum(String chequeNum) {
        this.chequeNum = chequeNum;
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

    public VoucherHeader getVoucherHeader() {
        return voucherHeader;
    }

    public void setVoucherHeader(VoucherHeader voucherHeader) {
        this.voucherHeader = voucherHeader;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lineId != null ? lineId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof VoucherLine)) {
            return false;
        }
        VoucherLine other = (VoucherLine) object;
        if ((this.lineId == null && other.lineId != null) || (this.lineId != null && !this.lineId.equals(other.lineId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.zahid.apps.web.pos.entity.VoucherLine[ lineId=" + lineId + " ]";
    }
}