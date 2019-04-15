package org.zahid.apps.web.pos.entity;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "XXUM_USER_DETAILS")
@Immutable
@XmlRootElement
@NamedQueries(value = {
        @NamedQuery(name = "UserDetail.findAll", query = "SELECT u FROM UserDetail u"),
        @NamedQuery(name = "UserDetail.findByGmId", query = "SELECT u FROM UserDetail u WHERE u.gmId = :gmId"),
        @NamedQuery(name = "UserDetail.findByGName", query = "SELECT u FROM UserDetail u WHERE u.gName = :gName"),
        @NamedQuery(name = "UserDetail.findByUId", query = "SELECT u FROM UserDetail u WHERE u.uId = :uId"),
        @NamedQuery(name = "UserDetail.findByUName", query = "SELECT u FROM UserDetail u WHERE upper(u.uName) = upper(:uName) "),
//        @NamedQuery(name = "UserDetail.findUniqueUserByUName", query = "SELECT distinct u.uId, u.uName, u.uPassword FROM UserDetail u WHERE upper(u.uName) = upper(:uName)"),
        @NamedQuery(name = "UserDetail.findByUPassword", query = "SELECT u FROM UserDetail u WHERE u.uPassword = :uPassword")})
public class UserDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GM_ID")
    @Id
    private Long gmId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "G_NAME")
    private String gName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "U_ID")
    private Long uId;
    @Size(max = 30)
    @Column(name = "U_NAME")
    private String uName;
    @Size(max = 240)
    @Column(name = "U_PASSWORD")
    private String uPassword;

    public UserDetail() {
    }

    public Long getGmId() {
        return gmId;
    }

    public void setGmId(Long gmId) {
        this.gmId = gmId;
    }

    public String getGName() {
        return gName;
    }

    public void setGName(String gName) {
        this.gName = gName;
    }

    public Long getUId() {
        return uId;
    }

    public void setUId(Long uId) {
        this.uId = uId;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getUPassword() {
        return uPassword;
    }

    public void setUPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gmId != null ? gmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserDetail other = (UserDetail) obj;
        if (!Objects.equals(this.gmId, other.gmId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.zahid.apps.web.pos.entity.UserDetail[ gmId=" + gmId + " && uId=" + uId + " ]";
    }
}
