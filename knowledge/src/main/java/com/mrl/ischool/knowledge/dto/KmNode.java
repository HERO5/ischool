package com.mrl.ischool.knowledge.dto;

import java.util.ArrayList;
import java.util.List;

public class KmNode
{
    private KmData data;
    private List<KmNode> children = new ArrayList();

    public KmData getData() { return this.data; }

    public void setData(KmData data) { this.data = data; }

    public List<KmNode> getChildren() { return this.children; }

    public void setChildren(List<KmNode> children) { this.children = children; }

    public String toString() { return "{\"data\":" + this.data + ", \"children\":" + this.children + "}"; }
}
