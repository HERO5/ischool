package com.mrl.ischool.knowledge.service;

import com.mrl.ischool.common.service.CommonService;
import com.mrl.ischool.knowledge.dao.KnowledgeDao;
import com.mrl.ischool.knowledge.entity.Knowledge;
import com.mrl.ischool.knowledge.entity.Progress;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeService
        extends CommonService<Knowledge, String>
{
    @Autowired
    private KnowledgeDao knowledgeDao;

    @Autowired
    public void setKnowledgeDao(KnowledgeDao knowledgeDao) { setCommonDao(knowledgeDao); }

    public List<Knowledge> findAll() { return this.knowledgeDao.findAll(); }

    public List<Knowledge> findByStuId(String stuId) { return this.knowledgeDao.queryKnowledgeByStu(stuId); }

    public List<Progress> getProgress(String stuId) { return this.knowledgeDao.queryProgressByStu(stuId); }
}
