package com.mrl.ischool.knowledge.dao;

import com.mrl.ischool.knowledge.entity.Knowledge;
import com.mrl.ischool.knowledge.entity.Progress;
import java.util.List;

public interface KnowledgeDaoCustom {
    List<Knowledge> queryKnowledgeByStu(String paramString);

    List<Progress> queryProgressByStu(String paramString);
}
