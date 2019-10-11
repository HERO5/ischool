package com.mrl.ischool.knowledge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "t_progress")
@NamedStoredProcedureQuery(name = "proc_get_progress_stuid", procedureName = "proc_get_progress_stuid", resultClasses = {Progress.class}, parameters = {@StoredProcedureParameter(mode = ParameterMode.IN, name = "stuid", type = String.class)})
public class Progress
{
    @Id
    @Column(name = "f_knowledge_id")
    private String knowledgeId;
    @Column(name = "f_sum_score")
    private int sumScore;
    @Column(name = "f_sum_point")
    private int sumPoint;

    public String getKnowledgeId() { return this.knowledgeId; }

    public void setKnowledgeId(String knowledgeId) { this.knowledgeId = knowledgeId; }

    public int getSumScore() { return this.sumScore; }

    public void setSumScore(int sumScore) { this.sumScore = sumScore; }

    public int getSumPoint() { return this.sumPoint; }

    public void setSumPoint(int sumPoint) { this.sumPoint = sumPoint; }

    public static int getProgressValue(int sumScore, int sumPoint) {
        double temp = sumScore / sumPoint * 1.0D;
        int progressValue = 1;
        if (temp < 0.063D) { progressValue = 1; }
        else if (temp >= 0.063D && temp < 0.188D) { progressValue = 2; }
        else if (temp >= 0.188D && temp < 0.29D) { progressValue = 3; }
        else if (temp >= 0.29D && temp < 0.42D) { progressValue = 4; }
        else if (temp >= 0.42D && temp < 0.57D) { progressValue = 5; }
        else if (temp >= 0.57D && temp < 0.69D) { progressValue = 6; }
        else if (temp >= 0.69D && temp < 0.813D) { progressValue = 7; }
        else if (temp >= 0.813D && temp < 0.938D) { progressValue = 8; }
        else { progressValue = 9; }
        return progressValue;
    }

    public String toString() { return "Progress{knowledgeId='" + this.knowledgeId + '\'' + ", sumScore=" + this.sumScore + ", sumPoint=" + this.sumPoint + '}'; }
}
