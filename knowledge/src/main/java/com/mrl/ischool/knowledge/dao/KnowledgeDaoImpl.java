package com.mrl.ischool.knowledge.dao;

import com.mrl.ischool.common.dao.CustomBaseSqlDaoImpl;
import com.mrl.ischool.knowledge.entity.Knowledge;
import com.mrl.ischool.knowledge.entity.Progress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KnowledgeDaoImpl
        extends CustomBaseSqlDaoImpl
        implements KnowledgeDaoCustom {

    public List<Knowledge> queryKnowledgeByStu(String stuId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("stuid", stuId);
        return queryProcedure("proc_get_knowledge_stuid", map, Knowledge.class);
    }



    public List<Progress> queryProgressByStu(String stuId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("stuid", stuId);
        return queryProcedure("proc_get_progress_stuid", map, Progress.class);
    }
}
