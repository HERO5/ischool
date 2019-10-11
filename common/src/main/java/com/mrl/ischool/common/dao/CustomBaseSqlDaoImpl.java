package com.mrl.ischool.common.dao;

import com.mrl.ischool.common.entity.PageModel;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomBaseSqlDaoImpl
{
    @Autowired
    private EntityManager em;

    public <T> List<T> queryProcedure(String procName, Object params, Class<T> tClass) {
        StoredProcedureQuery proc = this.em.createNamedStoredProcedureQuery(procName);

        if (params != null) {
            if (params instanceof List) {
                List<Object> paramList = (List)params;
                for (int i = 0, size = paramList.size(); i < size; i++) {
                    proc.setParameter(i + 1, paramList.get(i));
                }
            } else if (params instanceof Map) {
                Map<String, Object> paramMap = (Map)params;
                for (String key : paramMap.keySet()) {
                    proc.setParameter(key, paramMap.get(key));
                }
            }
        }
        return proc.getResultList();
    }

    public List<Map<String, Object>> querySqlObjects(String sql, Integer currentPage, Integer rowsInPage) { return querySqlObjects(sql, null, currentPage, rowsInPage); }

    public List<Map<String, Object>> querySqlObjects(String sql) { return querySqlObjects(sql, null, null, null); }

    public List<Map<String, Object>> querySqlObjects(String sql, List<Object> params) { return querySqlObjects(sql, params, null, null); }

    public List<Map<String, Object>> querySqlObjects(String sql, Object params, Integer currentPage, Integer rowsInPage) {
        Query qry = this.em.createNativeQuery(sql);
        SQLQuery s = (SQLQuery)qry.unwrap(SQLQuery.class);

        if (params != null) {
            if (params instanceof List) {
                List<Object> paramList = (List)params;
                for (int i = 0, size = paramList.size(); i < size; i++) {
                    qry.setParameter(i + 1, paramList.get(i));
                }
            } else if (params instanceof Map) {
                Map<String, Object> paramMap = (Map)params;
                for (String key : paramMap.keySet()) {
                    qry.setParameter(key, paramMap.get(key));
                }
            }
        }

        if (currentPage != null && rowsInPage != null) {

            qry.setFirstResult(rowsInPage.intValue() * (currentPage.intValue() - 1));

            qry.setMaxResults(rowsInPage.intValue());
        }
        s.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        try { resultList = s.list(); }
        catch (Exception exception) {  }
        finally
        { this.em.close(); }

        return resultList;
    }

    public PageModel<Map<String, Object>> querySqlObjects(String sql, String sbCount, Map<String, Object> params, Integer currentPage, Integer rowsInPage) {
        PageModel<Map<String, Object>> pageModel = new PageModel<Map<String, Object>>();

        List<Map<String, Object>> list = querySqlObjects(sql, params, currentPage, rowsInPage);

        pageModel.setList(list);

        if (currentPage == null || rowsInPage == null) {
            pageModel.setTotalCount((list == null) ? 0 : list.size());
        } else {
            Integer count = queryCountBySql(sbCount, params);

            pageModel.setCurrentPage(currentPage.intValue());
            pageModel.setTotalCount(count.intValue());
            pageModel.setPageSize(rowsInPage.intValue());

            int totalPage = 0;
            if (count.intValue() % rowsInPage.intValue() == 0) {
                totalPage = count.intValue() / rowsInPage.intValue();
            } else {
                totalPage = count.intValue() / rowsInPage.intValue() + 1;
            }

            pageModel.setTotalPage(totalPage);
        }

        return pageModel;
    }

    public int getCount(String sql) {
        String sqlCount = "select count(0) count_num from (" + sql + ") as total";
        List<Map<String, Object>> list = querySqlObjects(sqlCount);
        if (list.size() > 0) {
            return ((BigInteger)((Map)list.get(0)).get("count_num")).intValue();
        }

        return 0;
    }

    public String toSql(String _strSql) {
        String strNewSql = _strSql;

        if (strNewSql != null) {
            strNewSql = regReplace("'", "''", strNewSql);
        } else {
            strNewSql = "";
        }

        return strNewSql;
    }

    private String regReplace(String strFind, String strReplacement, String strOld) {
        String strNew = strOld;
        Pattern p = null;
        Matcher m = null;
        try {
            p = Pattern.compile(strFind);
            m = p.matcher(strOld);
            strNew = m.replaceAll(strReplacement);
        } catch (Exception exception) {}


        return strNew;
    }

    public List queryForList(String hql, List<Object> params) {
        Query query = this.em.createQuery(hql);
        List list = null;
        try {
            if (params != null && !params.isEmpty()) {
                for (int i = 0, size = params.size(); i < size; i++) {
                    query.setParameter(i + 1, params.get(i));
                }
            }
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.em.close();
        }
        return list;
    }

    public List queryByMapParams(String hql, Map<String, Object> params, Integer currentPage, Integer pageSize) {
        Query query = this.em.createQuery(hql);
        List list = null;
        try {
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    query.setParameter((String)entry.getKey(), entry.getValue());
                }
            }

            if (currentPage != null && pageSize != null) {
                query.setFirstResult((currentPage.intValue() - 1) * pageSize.intValue());
                query.setMaxResults(pageSize.intValue());
            }
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.em.close();
        }

        return list;
    }

    public List queryByMapParams(String hql, Map<String, Object> params) { return queryByMapParams(hql, params, null, null); }

    public List queryForList(String hql) { return queryForList(hql, null); }

    public PageModel queryForPage(String hql, int currentPage, int pageSize) {
        PageModel page = new PageModel();
        List list = null;
        Integer totalCount = Integer.valueOf(0);
        Integer totalPage = Integer.valueOf(0);
        try {
            int firstResult = (currentPage - 1) * pageSize;
            Query query = this.em.createQuery(hql);
            query.setMaxResults(pageSize);
            query.setFirstResult(firstResult);
            list = query.getResultList();

            Query query2 = this.em.createQuery(hql);
            List list2 = query2.getResultList();
            totalCount = Integer.valueOf((list2 == null) ? 0 : list2.size());
            if (totalCount.intValue() % pageSize == 0) {
                totalPage = Integer.valueOf(totalCount.intValue() / pageSize);
            } else {
                totalPage = Integer.valueOf(totalCount.intValue() / pageSize + 1);
            }

            page.setCurrentPage(currentPage);
            page.setList(list);
            page.setPageSize(pageSize);
            page.setTotalCount(totalCount.intValue());
            page.setTotalPage(totalPage.intValue());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.em.close();
        }
        return page;
    }

    public PageModel queryForPageWithParams(String hql, String hqlCount, Map<String, Object> params, int currentPage, int pageSize) {
        PageModel page = new PageModel();
        List<Object> list = null;
        Integer totalCount = Integer.valueOf(0);
        Integer totalPage = Integer.valueOf(0);

        Query query = this.em.createQuery(hql);

        try {
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    query.setParameter((String)entry.getKey(), entry.getValue());
                }
            }

            query.setMaxResults(pageSize);
            query.setFirstResult((currentPage - 1) * pageSize);
            list = query.getResultList();


            totalCount = Integer.valueOf(queryCount(hqlCount, params).intValue());
            if (totalCount.intValue() % pageSize == 0) {
                totalPage = Integer.valueOf(totalCount.intValue() / pageSize);
            } else {
                totalPage = Integer.valueOf(totalCount.intValue() / pageSize + 1);
            }

            page.setCurrentPage(currentPage);
            page.setPageSize(pageSize);
            page.setList(list);
            page.setTotalCount(totalCount.intValue());
            page.setTotalPage(totalPage.intValue());
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.em.close();
        }
        return page;
    }


    public PageModel queryForPageWithParams(String hql, Map<String, Object> params, int currentPage, int pageSize) {
        PageModel page = new PageModel();
        List<Object> list = null;
        Integer totalCount = Integer.valueOf(0);
        Integer totalPage = Integer.valueOf(0);

        Query query = this.em.createQuery(hql);

        try {
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    query.setParameter((String)entry.getKey(), entry.getValue());
                }
            }

            query.setMaxResults(pageSize);
            query.setFirstResult((currentPage - 1) * pageSize);
            list = query.getResultList();

            Query queryTotal = this.em.createQuery(hql);

            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    queryTotal.setParameter((String)entry.getKey(), entry.getValue());
                }
            }

            List<Object> totlaList = queryTotal.getResultList();
            totalCount = Integer.valueOf((totlaList == null) ? 0 : totlaList.size());
            if (totalCount.intValue() % pageSize == 0) {
                totalPage = Integer.valueOf(totalCount.intValue() / pageSize);
            } else {
                totalPage = Integer.valueOf(totalCount.intValue() / pageSize + 1);
            }

            page.setCurrentPage(currentPage);
            page.setPageSize(pageSize);
            page.setList(list);
            page.setTotalCount(totalCount.intValue());
            page.setTotalPage(totalPage.intValue());
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.em.close();
        }
        return page;
    }

    public PageModel queryForPageBySql(String sql, Integer currentPage, Integer pageSize) {
        PageModel page = new PageModel();
        Integer totalCount = Integer.valueOf(0);
        Integer totalPage = Integer.valueOf(0);

        Query qry = this.em.createNativeQuery(sql);
        Query qry2 = this.em.createNativeQuery(sql);
        SQLQuery s = (SQLQuery)qry.unwrap(SQLQuery.class);
        if (currentPage != null && pageSize != null) {

            qry.setFirstResult(pageSize.intValue() * (currentPage.intValue() - 1));

            qry.setMaxResults(pageSize.intValue());
        }
        s.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
        List<Map> resultList = new ArrayList<Map>();
        List list = qry2.getResultList();
        totalCount = Integer.valueOf((list == null) ? 0 : list.size());
        if (totalCount.intValue() % pageSize.intValue() == 0) {
            totalPage = Integer.valueOf(totalCount.intValue() / pageSize.intValue());
        } else {
            totalPage = Integer.valueOf(totalCount.intValue() / pageSize.intValue() + 1);
        }
        try {
            resultList = s.list();
            page.setCurrentPage(currentPage.intValue());
            page.setPageSize(pageSize.intValue());
            page.setList(resultList);
            page.setTotalCount(totalCount.intValue());
            page.setTotalPage(totalPage.intValue());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.em.close();
        }
        return page;
    }

    public PageModel queryForPageBySql(String sql, Map<String, Object> params, Integer currentPage, Integer pageSize) {
        PageModel page = new PageModel();
        Integer totalCount = Integer.valueOf(0);
        Integer totalPage = Integer.valueOf(0);

        Query qry = this.em.createNativeQuery(sql);
        Query qry2 = this.em.createNativeQuery(sql);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            qry.setParameter((String)entry.getKey(), entry.getValue());
            qry2.setParameter((String)entry.getKey(), entry.getValue());
        }

        SQLQuery s = (SQLQuery)qry.unwrap(SQLQuery.class);
        if (currentPage != null && pageSize != null) {

            qry.setFirstResult(pageSize.intValue() * (currentPage.intValue() - 1));

            qry.setMaxResults(pageSize.intValue());
        }
        s.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
        List<Map> resultList = new ArrayList<Map>();
        List list = qry2.getResultList();
        totalCount = Integer.valueOf((list == null) ? 0 : list.size());
        if (totalCount.intValue() % pageSize.intValue() == 0) {
            totalPage = Integer.valueOf(totalCount.intValue() / pageSize.intValue());
        } else {
            totalPage = Integer.valueOf(totalCount.intValue() / pageSize.intValue() + 1);
        }
        try {
            resultList = s.list();
            page.setCurrentPage(currentPage.intValue());
            page.setPageSize(pageSize.intValue());
            page.setList(resultList);
            page.setTotalCount(totalCount.intValue());
            page.setTotalPage(totalPage.intValue());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.em.close();
        }
        return page;
    }

    public Long queryCount(String hql, Map<String, Object> params) {
        Query query = this.em.createQuery(hql);
        Long count = null;
        try {
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    query.setParameter((String)entry.getKey(), entry.getValue());
                }
            }
            count = (Long)query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.em.close();
        }

        return count;
    }

    public Integer queryCountBySql(String sql, Map<String, Object> params) {
        Integer count = null;
        try {
            Query query = this.em.createNativeQuery(sql);
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    query.setParameter((String)entry.getKey(), entry.getValue());
                }
            }

            Object obj = query.getSingleResult();
            if (obj instanceof BigInteger) {
                count = Integer.valueOf(((BigInteger)obj).intValue());
            } else {
                count = (Integer)obj;
            }
        } finally {

            if (this.em != null) {
                this.em.close();
            }
        }
        return count;
    }

    public int executeSql(String sql, List<Object> params) {
        try {
            Query query = this.em.createNativeQuery(sql);
            if (params != null && !params.isEmpty()) {
                for (int i = 0, size = params.size(); i < size; i++) {
                    query.setParameter(i + 1, params.get(i));
                }
            }
            return query.executeUpdate();
        } finally {
            if (this.em != null)
                this.em.close();
        }
    }
}
