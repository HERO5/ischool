package com.mrl.ischool.common.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.mrl.ischool.common.entity.IdEntity;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class MysqlSequenceIdEntity
        extends IdEntity
{
    protected String id;

    @Id
    @Column(length = 32, nullable = true)
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    @GeneratedValue(generator = "sys_uuid")
    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }
}
