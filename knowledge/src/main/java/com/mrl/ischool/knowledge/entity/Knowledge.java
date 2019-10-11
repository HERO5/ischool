package com.mrl.ischool.knowledge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "t_knowledge")
@NamedStoredProcedureQuery(name = "proc_get_knowledge_stuid", procedureName = "proc_get_knowledge_stuid", resultClasses = {Knowledge.class}, parameters = {@StoredProcedureParameter(mode = ParameterMode.IN, name = "stuid", type = String.class)})
public class Knowledge
{
    @Id
    @Column(name = "f_knowledge_id")
    private String knowledgeId;
    @Column(name = "f_name", nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "f_course_id", nullable = false)
    private Course course;
    @Column(name = "f_require_level", nullable = true)
    private String requireLevel;
    @Column(name = "f_importance")
    private int importance;
    @Column(name = "f_memo")
    private String memo;
    @ManyToOne
    @JoinColumn(name = "f_parent_id")
    private Knowledge parent;

    public String getKnowledgeId() { return this.knowledgeId; }

    public void setKnowledgeId(String knowledgeId) { this.knowledgeId = knowledgeId; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public Course getCourse() { return this.course; }

    public void setCourse(Course course) { this.course = course; }

    public String getRequireLevel() { return this.requireLevel; }

    public void setRequireLevel(String requireLevel) { this.requireLevel = requireLevel; }

    public int getImportance() { return this.importance; }

    public void setImportance(int importance) { this.importance = importance; }

    public String getMemo() { return this.memo; }

    public void setMemo(String memo) { this.memo = memo; }

    public Knowledge getParent() { return this.parent; }

    public void setParent(Knowledge parent) { this.parent = parent; }

    public String toString() { return "Knowledge{knowledgeId='" + this.knowledgeId + '\'' + ", name='" + this.name + '\'' + ", courseId='" + this.course + '\'' + ", requireLevel='" + this.requireLevel + '\'' + ", importance=" + this.importance + ", memo='" + this.memo + '\'' + '}'; }
}
