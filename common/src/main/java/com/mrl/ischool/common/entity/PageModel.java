package com.mrl.ischool.common.entity;

import java.io.Serializable;
import java.util.List;

public class PageModel<E>
        extends Object
        implements Serializable
{
    private static final long serialVersionUID = 3265524976080127173L;
    private int totalCount;
    private int pageSize = 10;

    private int totalPage;

    private int currentPage = 1;

    private List<E> list;

    private String url;

    private boolean isSearchResult;

    public PageModel() {}

    public PageModel(int pageSize) { this.pageSize = pageSize; }

    public int getTotalCount() { return this.totalCount; }

    public void setTotalCount(int totalCount) { this.totalCount = totalCount; }

    public int getPageSize() { return this.pageSize; }

    public void setPageSize(int pageSize) { this.pageSize = pageSize; }

    public int getTotalPage() { return this.totalPage; }

    public void setTotalPage(int totalPage) { this.totalPage = totalPage; }

    public int getCurrentPage() { return this.currentPage; }

    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }

    public List<E> getList() { return this.list; }

    public void setList(List<E> list) { this.list = list; }

    public String getUrl() { return this.url; }

    public void setUrl(String url) { this.url = url; }

    public boolean isSearchResult() { return this.isSearchResult; }

    public void setSearchResult(boolean searchResult) { this.isSearchResult = searchResult; }
}
