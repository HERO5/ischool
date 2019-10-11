package com.mrl.ischool.common.dto;

import java.util.HashMap;
import java.util.Map;

public class CommonQueryDTO
{
    private Integer currentPage;
    private Integer pageSize;
    private Map<String, String> condition = new HashMap();

    public void addParam(String name, String value) { this.condition.put(name, value); }

    public void addParams(Map<String, String> map) { this.condition.putAll(map); }

    public void removeParam(String key) { this.condition.remove(key); }

    public Map<String, String> getCondition() { return this.condition; }

    public void setCondition(Map<String, String> condition) { this.condition = condition; }

    public Integer getCurrentPage() { return this.currentPage; }

    public void setCurrentPage(Integer currentPage) { this.currentPage = currentPage; }

    public Integer getPageSize() { return this.pageSize; }

    public void setPageSize(Integer pageSize) { this.pageSize = pageSize; }
}
