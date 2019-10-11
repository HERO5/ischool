package com.mrl.ischool.knowledge.dao;

import com.mrl.ischool.common.dao.CommonDao;
import com.mrl.ischool.knowledge.entity.Knowledge;

public interface KnowledgeDao extends KnowledgeDaoCustom, CommonDao<Knowledge, String> {}