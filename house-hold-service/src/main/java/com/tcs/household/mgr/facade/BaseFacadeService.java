package com.tcs.household.mgr.facade;

import com.tcs.household.enums.MessageCode;
import com.tcs.household.exception.BizException;
import org.springframework.util.StringUtils;

public class BaseFacadeService {

    /**
     * 验证参数是否为空
     *
     * @param objects
     */
    protected void checkParam(Object... objects) {
        for (Object object : objects) {
            if (null == object || (object instanceof String && StringUtils.isEmpty((String) object))) {
                 throw new BizException(MessageCode.REQUEST_PARAM_NULL_ERROR);
            }
        }
    }

    protected void checkPageParam(Integer pageNum,Integer pageSize){
        if(pageNum==null || pageSize ==null || pageNum < 1 || pageSize < 1){
            throw new BizException(MessageCode.PAGING_PARAM_ERROR);
        }
    }


}
