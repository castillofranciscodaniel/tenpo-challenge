package com.org.tenpo.challenge.core.model;

import java.util.Collection;

public class SimplePage<E> {
    private Integer page;
    private Integer pageSize;
    private Long totalPages;
    private Long totalElement;
    private Collection<E> content;

    public SimplePage() {
    }

    public SimplePage(Integer page, Integer pageSize, Long totalPages, Long totalElement, Collection<E> content) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElement = totalElement;
        this.content = content;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(Long totalElement) {
        this.totalElement = totalElement;
    }

    public Collection<E> getContent() {
        return content;
    }

    public void setContent(Collection<E> content) {
        this.content = content;
    }
}
