package com.mrl.ischool.knowledge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_course")
public class Course
{
    @Id
    @Column(name = "f_course_id")
    private String courseId;
    @Column(name = "f_name")
    private String name;
    @Column(name = "f_memo")
    private String memo;
    @Column(name = "f_if_required")
    private int ifRequired;
    @Column(name = "f_credit")
    private int credit;

    public String getCourseId() { return this.courseId; }

    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public int getIfRequired() { return this.ifRequired; }

    public void setIfRequired(int ifRequired) { this.ifRequired = ifRequired; }

    public int getCredit() { return this.credit; }

    public void setCredit(int credit) { this.credit = credit; }

    public String getMemo() { return this.memo; }

    public void setMemo(String memo) { this.memo = memo; }

    public String toString() { return "Course{courseId='" + this.courseId + '\'' + ", name='" + this.name + '\'' + ", ifRequired='" + this.ifRequired + '\'' + ", credit='" + this.credit + '\'' + ", memo='" + this.memo + '\'' + '}'; }
}
