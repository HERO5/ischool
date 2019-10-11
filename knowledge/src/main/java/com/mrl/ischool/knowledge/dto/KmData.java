package com.mrl.ischool.knowledge.dto;

import org.apache.commons.lang3.StringUtils;

public class KmData
{
    private String id;
    private long created;
    private String text;
    private int priority;
    private int progress;
    private String note;
    private String hyperlink;

    public KmData(String id, long created, String text, int priority, int progress, String note, String hyperlink) {
        this.id = id;
        this.created = created;
        this.text = text;
        this.priority = priority;
        this.progress = progress;
        this.note = note;
        this.hyperlink = hyperlink;
    }

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public long getCreated() { return this.created; }

    public void setCreated(long created) { this.created = created; }

    public String getText() { return this.text; }

    public void setText(String text) { this.text = text; }

    public int getPriority() { return this.priority; }

    public void setPriority(int priority) { this.priority = priority; }

    public int getProgress() { return this.progress; }

    public void setProgress(int progress) { this.progress = progress; }

    public String getNote() { return this.note; }

    public void setNote(String note) { this.note = note; }

    public String getHyperlink() { return this.hyperlink; }

    public void setHyperlink(String hyperlink) { this.hyperlink = hyperlink; }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("\"id\":\"" + this.id + "\"");
        sb.append(", \"created\":" + this.created);
        sb.append(", \"text\":\"" + this.text + "\"");
        sb.append(", \"priority\":" + this.priority);
        if (this.progress > -1) sb.append(", \"progress\":" + this.progress);
        if (StringUtils.isNotBlank(this.note)) sb.append(", \"note\":\"" + this.note + "\"");
        if (StringUtils.isNotBlank(this.hyperlink)) sb.append(", \"hyperlink\":\"" + this.hyperlink + "\"");
        return sb.toString().trim();
    }
}
