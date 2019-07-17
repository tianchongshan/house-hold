package com.tcs.household.model.request;

import lombok.Data;

/**
 * 分页请求入参积累（后台用）
 */
@Data
public abstract class AbstractPagingBaseRequest extends AbstractBaseRequest {
    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 每页件数
     */
    private Integer pageSize;
}
