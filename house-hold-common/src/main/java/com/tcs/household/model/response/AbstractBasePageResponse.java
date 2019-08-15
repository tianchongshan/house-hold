package com.tcs.household.model.response;

import com.github.pagehelper.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页返回基类
 * @param <T>
 */
@Data
public abstract class AbstractBasePageResponse<T> implements Serializable {

    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 每页件数
     */
    private Integer pageSize;

    /**
     * 总件数
     */
    private Long total;

    /**
     * 业务数据
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
    }

    /**
     * 拷贝数据
     * @param page
     */
    public void copyPageData(Page<T> page) {
        this.pageNum = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.total = page.getTotal();
        this.list = new ArrayList<>();
        this.list.addAll(page.getResult());
    }
}
