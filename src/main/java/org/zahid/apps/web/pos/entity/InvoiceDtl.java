package org.zahid.apps.web.pos.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * The persistent class for the XXIM_INVOICE_DTL database table.
 * 
 */
@Entity
@Table(name = "XXIM_INVOICE_DTL")
@NamedQuery(name = "InvoiceDtl.findAll", query = "SELECT i FROM InvoiceDtl i")
public class InvoiceDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "XXIM_INVOICE_DTL_INVDTLNUM_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXIM_INVOICE_DTL_INVDTLNUM_GENERATOR")
	@Column(name = "INV_DTL_NUM")
	private Long invDtlNum;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;

	@Column(name = "ITEM_PRICE")
	private BigDecimal itemPrice;

	@Column(name = "LAST_UPDATE_DATE")
	private Timestamp lastUpdateDate;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	private BigDecimal qnty;

	// bi-directional many-to-one association to InvoiceMain
	@ManyToOne
	@JoinColumn(name = "INV_NUM")
	private InvoiceMain invoiceMain;

	// bi-directional many-to-one association to Item
	@ManyToOne
	@JoinColumn(name = "ITEM_CODE")
	private Item item;

	public InvoiceDtl() {
	}

	public Long getInvDtlNum() {
		return invDtlNum;
	}

	public void setInvDtlNum(Long invDtlNum) {
		this.invDtlNum = invDtlNum;
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

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
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

	public BigDecimal getQnty() {
		return qnty;
	}

	public void setQnty(BigDecimal qnty) {
		this.qnty = qnty;
	}

	public InvoiceMain getInvoiceMain() {
		return invoiceMain;
	}

	public void setInvoiceMain(InvoiceMain invoiceMain) {
		this.invoiceMain = invoiceMain;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (invDtlNum != null ? invDtlNum.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof InvoiceDtl)) {
			return false;
		}
		InvoiceDtl other = (InvoiceDtl) object;
		if ((this.invDtlNum == null && other.invDtlNum != null) || (this.invDtlNum != null && !this.invDtlNum.equals(other.invDtlNum))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "org.zahid.apps.web.pos.entity.InvoiceDtl[ invDtlNum=" + invDtlNum + " ]";
	}
}