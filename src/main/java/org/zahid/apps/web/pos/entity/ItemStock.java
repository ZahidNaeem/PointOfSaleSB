package org.zahid.apps.web.pos.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the XXIM_ITEM_STOCK database table.
 */
@Entity
@Table(name = "XXIM_ITEM_STOCK")
@NamedQueries({
        @NamedQuery(name = "ItemStock.findAll", query = "SELECT i FROM ItemStock i"),
        @NamedQuery(name = "ItemStock.findAllByItem", query = "SELECT i FROM ItemStock i where i.item = ?1"),
        @NamedQuery(name = "ItemStock.generateID", query = "SELECT coalesce(max(itemStockId), 0) + 1 FROM ItemStock i")
})
public class ItemStock implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
//    @SequenceGenerator(name = "XXIM_ITEM_STOCK_ITEMSTOCKID_GENERATOR")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXIM_ITEM_STOCK_ITEMSTOCKID_GENERATOR")
    @Column(name = "ITEM_STOCK_ID")
    private Long itemStockId;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "ITEM_STOCK_DATE")
    private Date itemStockDate;

    @Column(name = "LAST_UPDATE_DATE")
    private Timestamp lastUpdateDate;

    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

    private BigDecimal qnty;

    private String remarks;

    // bi-directional many-to-one association to Item
    @ManyToOne
    @JoinColumn(name = "ITEM_CODE")
    private Item item;

    public ItemStock() {
    }

    public Long getItemStockId() {
        return itemStockId;
    }

    public void setItemStockId(Long itemStockId) {
        this.itemStockId = itemStockId;
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

    public Date getItemStockDate() {
        return itemStockDate;
    }

    public void setItemStockDate(Date itemStockDate) {
        this.itemStockDate = itemStockDate;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        hash += (itemStockId != null ? itemStockId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ItemStock)) {
            return false;
        }
        ItemStock other = (ItemStock) object;
        if ((this.itemStockId == null && other.itemStockId != null) || (this.itemStockId != null && !this.itemStockId.equals(other.itemStockId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.zahid.apps.web.pos.entity.ItemStock[ itemStockId=" + itemStockId + " ]";
    }
}