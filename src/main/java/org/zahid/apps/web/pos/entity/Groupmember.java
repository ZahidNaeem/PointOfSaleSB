package org.zahid.apps.web.pos.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The persistent class for the XXUM_GROUPMEMBERS database table.
 */
@Entity
@Table(name = "XXUM_GROUPMEMBERS")
@NamedQueries({
        @NamedQuery(name = "Groupmember.findAll", query = "SELECT g FROM Groupmember g"),
        @NamedQuery(name = "Groupmember.findBygMember", query = "SELECT g FROM Groupmember g WHERE upper(gMember) = ?1")})
public class Groupmember implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "XXUM_GROUPMEMBERS_GMID_GENERATOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXUM_GROUPMEMBERS_GMID_GENERATOR")
    @Column(name = "GM_ID")
    private Long gmId;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;

    @Column(name = "G_MEMBER")
    private String gMember;

    @Column(name = "G_NAME")
    private String gName;

    @Column(name = "LAST_UPDATE_DATE")
    private Timestamp lastUpdateDate;

    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

    public Groupmember() {
    }

    public Long getGmId() {
        return gmId;
    }

    public void setGmId(Long gmId) {
        this.gmId = gmId;
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

    public String getgMember() {
        return gMember;
    }

    public void setgMember(String gMember) {
        this.gMember = gMember;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gmId != null ? gmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Groupmember)) {
            return false;
        }
        Groupmember other = (Groupmember) object;
        if ((this.gmId == null && other.gmId != null) || (this.gmId != null && !this.gmId.equals(other.gmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.zahid.apps.web.pos.entity.Groupmember[ gmId=" + gmId + " ]";
    }
}