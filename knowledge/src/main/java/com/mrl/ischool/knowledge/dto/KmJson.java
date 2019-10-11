package com.mrl.ischool.knowledge.dto;

import com.mrl.ischool.knowledge.entity.Knowledge;
import com.mrl.ischool.knowledge.entity.Progress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class KmJson
{
    private KmNode root;
    private String template;
    private String theme;
    private String version;

    public KmJson(KmNode root, String template, String theme, String version) {
        this.root = root;
        this.template = template;
        this.theme = theme;
        this.version = version;
    }

    public KmNode getRoot() { return this.root; }

    public void setRoot(KmNode root) { this.root = root; }

    public String getTemplate() { return this.template; }

    public void setTemplate(String template) { this.template = template; }

    public String getTheme() { return this.theme; }

    public void setTheme(String theme) { this.theme = theme; }

    public String getVersion() { return this.version; }

    public void setVersion(String version) { this.version = version; }

    public static KmJson getJson(List<Knowledge> ks, List<Progress> ps) {
        Map<String, KmNode> flags = new HashMap<String, KmNode>();
        Map<String, Integer> progress = new HashMap<String, Integer>();
        KmNode root = null;

        //if (ps != null) for (Iterator iterator = ps.iterator(); iterator.hasNext(); progress.put(p.getKnowledgeId(), Integer.valueOf((p = (Progress)iterator.next()).getProgressValue(p.getSumScore(), p.getSumPoint()))));
        if(ps !=null) for(Progress p : ps) progress.put(p.getKnowledgeId(), p.getProgressValue(p.getSumScore(), p.getSumPoint()));

        for (Knowledge k : ks) flags.put(k.getKnowledgeId(), new KmNode());
        for (int i = 0; i < ks.size(); i++) {
            if (((Knowledge)ks.get(i)).getParent() != null && !flags.containsKey(((Knowledge)ks.get(i)).getParent().getKnowledgeId())) {
                ks.add(((Knowledge)ks.get(i)).getParent());
                flags.put(((Knowledge)ks.get(i)).getParent().getKnowledgeId(), new KmNode());
            }
        }
        for (Knowledge k : ks) {
            KmNode temp = (KmNode)flags.get(k.getKnowledgeId());
            temp.setData(new KmData(k.getKnowledgeId(), System.currentTimeMillis(), k.getName(), k.getImportance(),
                    progress.containsKey(k.getKnowledgeId()) ? ((Integer)progress.get(k.getKnowledgeId())).intValue() : 0, k.getMemo(), null));
            if (k.getParent() == null) {
                root = temp; continue;
            }
            ((KmNode)flags.get(k.getParent().getKnowledgeId())).getChildren().add(temp);
        }
        return new KmJson(root, "default", "fresh-blue", "1.4.33");
    }

    public String toString() { return "{\"root\":" + this.root + ", \"template\":\"" + this.template + "\", \"theme\":\"" + this.theme + "\", \"version\":\"" + this.version + "\"" + '}'; }
}
