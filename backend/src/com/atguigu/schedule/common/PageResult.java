package com.atguigu.schedule.common;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应数据封装
 * 
 * @param <T> 列表数据类型
 */
public class PageResult<T> implements Serializable {
    
    private static final long serialVersionUID = 20251124L;
    
    /** 当前页数据列表 */
    private List<T> records;
    
    /** 总记录数 */
    private Long total;
    
    /** 当前页码 */
    private Integer pageNum;
    
    /** 每页条数 */
    private Integer pageSize;
    
    /** 总页数 */
    private Integer pages;

    // ==================== 构造方法 ====================
    
    public PageResult() {
    }

    public PageResult(List<T> records, Long total, Integer pageNum, Integer pageSize) {
        this.records = records;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = (int) Math.ceil((double) total / pageSize);
    }

    // ==================== 静态工厂方法 ====================
    
    /**
     * 创建分页结果（完整参数）
     */
    public static <T> PageResult<T> of(List<T> records, Long total, Integer pageNum, Integer pageSize) {
        return new PageResult<>(records, total, pageNum, pageSize);
    }
    
    /**
     * 创建分页结果（简化版）
     */
    public static <T> PageResult<T> of(List<T> records, Long total) {
        return new PageResult<>(records, total, null, null);
    }

    // ==================== Getter/Setter ====================
    
    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
