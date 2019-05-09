package org.zahid.apps.web.pos.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the XXIM_PARTY_BALANCE database table.
 */
@Entity
@Table(name = "XXIM_PARTY_BALANCE")
@NamedQueries({
        @NamedQuery(name = "PartyBalance.findAll", query = "SELECT p FROM PartyBalance p"),
        @NamedQuery(name = "PartyBalance.generateID", query = "SELECT coalesce(max(partyBalanceId), 0) + 1 FROM PartyBalance p")
})
public class PartyBalance implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
//    @SequenceGenerator(name = "XXIM_PARTY_BALANCE_PARTYBALANCEID_GENERATOR")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXIM_PARTY_BALANCE_PARTYBALANCEID_GENERATOR")
    @Column(name = "PARTY_BALANCE_ID")
    private Long partyBalanceId;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;

    @Column(name = "LAST_UPDATE_DATE")
    private Timestamp lastUpdateDate;

    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "PARTY_BALANCE_DATE")
    private Date partyBalanceDate;

    private String remarks;

    // bi-directional many-to-one association to Party
    @ManyToOne
//    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "PARTY_CODE")
    private Party party;

    public PartyBalance() {
    }

    public Long getPartyBalanceId() {
        return partyBalanceId;
    }

    public void setPartyBalanceId(Long partyBalanceId) {
        this.partyBalanceId = partyBalanceId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public Date getPartyBalanceDate() {
        return partyBalanceDate;
    }

    public void setPartyBalanceDate(Date partyBalanceDate) {
        this.partyBalanceDate = partyBalanceDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        hash += (partyBalanceId != null ? partyBalanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PartyBalance)) {
            return false;
        }
        PartyBalance other = (PartyBalance) object;
        if ((this.partyBalanceId == null && other.partyBalanceId != null) || (this.partyBalanceId != null && !this.partyBalanceId.equals(other.partyBalanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.zahid.apps.web.pos.entity.PartyBalance[ partyBalanceId=" + partyBalanceId + " ]";
    }
}