package org.zahid.apps.web.pos.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the XXIM_INVOICE_MAIN database table.
 */
@Entity
@Table(name = "XXIM_INVOICE_MAIN")
@NamedQuery(name = "InvoiceMain.findAll", query = "SELECT i FROM InvoiceMain i")
public class InvoiceMain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "XXIM_INVOICE_MAIN_INVNUM_GENERATOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXIM_INVOICE_MAIN_INVNUM_GENERATOR")
    @Column(name = "INV_NUM")
    private Long invNum;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "INV_DATE")
    private Date invDate;

    @Column(name = "INV_TYPE")
    private String invType;

    @Column(name = "LAST_UPDATE_DATE")
    private Timestamp lastUpdateDate;

    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

    @Column(name = "PAID_AMT")
    private BigDecimal paidAmt;

    private String reason;

    private String remarks;

    // bi-directional many-to-one association to InvoiceDtl
    @OneToMany(mappedBy = "invoiceMain")
    private List<InvoiceDtl> invoiceDtls;

    // bi-directional many-to-one association to InvoiceMain
    @ManyToOne
    @JoinColumn(name = "REF_NUM")
    private InvoiceMain invoiceMain;

    // bi-directional many-to-one association to InvoiceMain
    @OneToMany(mappedBy = "invoiceMain")
    private List<InvoiceMain> invoiceMains;

    // bi-directional many-to-one association to Party
    @ManyToOne
    @JoinColumn(name = "PARTY_CODE")
    private Party party;

    public InvoiceMain() {
    }

    public Long getInvNum() {
        return invNum;
    }

    public void setInvNum(Long invNum) {
        this.invNum = invNum;
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

    public Date getInvDate() {
        return invDate;
    }

    public void setInvDate(Date invDate) {
        this.invDate = invDate;
    }

    public String getInvType() {
        return invType;
    }

    public void setInvType(String invType) {
        this.invType = invType;
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

    public BigDecimal getPaidAmt() {
        return paidAmt;
    }

    public void setPaidAmt(BigDecimal paidAmt) {
        this.paidAmt = paidAmt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<InvoiceDtl> getInvoiceDtls() {
        return invoiceDtls;
    }

    public void setInvoiceDtls(List<InvoiceDtl> invoiceDtls) {
        this.invoiceDtls = invoiceDtls;
    }

    public InvoiceMain getInvoiceMain() {
        return invoiceMain;
    }

    public void setInvoiceMain(InvoiceMain invoiceMain) {
        this.invoiceMain = invoiceMain;
    }

    public List<InvoiceMain> getInvoiceMains() {
        return invoiceMains;
    }

    public void setInvoiceMains(List<InvoiceMain> invoiceMains) {
        this.invoiceMains = invoiceMains;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invNum != null ? invNum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof InvoiceMain)) {
            return false;
        }
        InvoiceMain other = (InvoiceMain) object;
        if ((this.invNum == null && other.invNum != null) || (this.invNum != null && !this.invNum.equals(other.invNum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.zahid.apps.web.pos.entity.InvoiceMain[ invNum=" + invNum + " ]";
    }
}