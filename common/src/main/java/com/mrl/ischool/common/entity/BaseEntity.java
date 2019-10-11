package com.mrl.ischool.common.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public class BaseEntity
        extends MysqlSequenceIdEntity
        implements Serializable
{
    private static final long serialVersionUID = -4498233384948128317L;
    protected Date createDate;
    protected Date updateDate;

    @Column(name = "create_date")
    public Date getCreateDate() { return this.createDate; }

    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    @Column(name = "update_date")
    public Date getUpdateDate() { return this.updateDate; }

    public void setUpdateDate(Date updateDate) { this.updateDate = updateDate; }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity)object;
            if (getId() == null || baseEntity.getId() == null) {
                return false;
            }
            return getId().equals(baseEntity.getId());
        }

        return false;
    }

    public int hashCode() { return (this.id == null) ? System.identityHashCode(this) : (getClass().getName() + getId()).hashCode(); }

    @PrePersist
    public void prePersist() {
        if (this.createDate == null) {
            setCreateDate(new Date());
        }
        setUpdateDate(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        if (this.updateDate == null)
            setUpdateDate(new Date());
    }
}
