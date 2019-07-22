package com.tcs.household.codeUtils.model;

import com.github.pagehelper.Page;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询响应基类
 * @author xiaoj
 *
 */
@Getter
@Setter
public abstract class AbstractBasePagingResponse<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8097471607767645894L;

	/** 
	 * 总件数 
	 */
    private long total;
    
    /** 
     * 总页数 
     */
    private int pages;
    
    /** 
     * 当前页 
     */
    private int pageNum;

    /** 
     * 每页大小 
     */
    private int pageSize;
    
    /**
     * 请求数据
     */
    private List<T> list;

    /**
     * 拷贝分页信息
     * @param page
     */
    @SuppressWarnings("rawtypes")
    public void copyPageInfo(Page page) {
        this.pageNum = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.total = page.getTotal();
        this.pages = page.getPages();
    }

    /**
     * 拷贝数据
     * @param page
     */
    public void copyPageData(Page<T> page) {
        this.pageNum = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.total = page.getTotal();
        this.pages = page.getPages();
        this.list = new ArrayList<>();
        this.list.addAll(page.getResult());
    }
}
