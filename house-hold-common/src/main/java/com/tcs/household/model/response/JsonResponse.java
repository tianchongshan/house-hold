package com.tcs.household.model.response;

import com.tcs.household.enums.MessageCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResponse<T> implements Serializable{

    private static final long serialVersionUID = 8594037881455160578L;
    /**
     * 返回Code
     */
    private String code;

    /**
     * 消息体
     */
    private String msg;

    /**
     * 数据体
     */
    private T data;

    public static <T> JsonResponse<T> success() {
        JsonResponse<T> resp = new JsonResponse<>();
        resp.setCode(MessageCode.SUCCESS.getCode());
        resp.setMsg(MessageCode.SUCCESS.getMessage());
        return resp;
    }

    public static <T> JsonResponse<T> success(T d) {
        JsonResponse<T> resp = new JsonResponse<>();
        resp.setCode(MessageCode.SUCCESS.getCode());
        resp.setMsg(MessageCode.SUCCESS.getMessage());
        resp.setData(d);
        return resp;
    }

    public static <T> JsonResponse<T> success(String msg, T d) {
        JsonResponse<T> resp = new JsonResponse<>();
        resp.setCode(MessageCode.SUCCESS.getCode());
        resp.setMsg(msg);
        resp.setData(d);
        return resp;
    }

    public static <T> JsonResponse<T> error() {
        JsonResponse<T> resp = new JsonResponse<>();
        resp.setCode(MessageCode.FAIL.getCode());
        resp.setMsg(MessageCode.FAIL.getMessage());
        return resp;
    }
    public static <T> JsonResponse<T> error(String msg) {
        JsonResponse<T> resp = new JsonResponse<>();
        resp.setCode(MessageCode.FAIL.getCode());
        resp.setMsg(msg);
        return resp;
    }

    public static <T> JsonResponse<T> error(String code, String msg) {
        JsonResponse<T> resp = new JsonResponse<>();
        resp.setCode(code);
        resp.setMsg(msg);
        return resp;
    }

    public static <T> JsonResponse<T> error(String code, String msg, T d) {
        JsonResponse<T> resp = new JsonResponse<>();
        resp.setCode(code);
        resp.setMsg(msg);
        resp.setData(d);
        return resp;
    }

    public static <T> JsonResponse<T> error(Exception e) {
        JsonResponse<T> resp = new JsonResponse<>();
        resp.setCode(MessageCode.FAIL.getCode());
        resp.setMsg(e.getMessage());
        return resp;
    }
}
