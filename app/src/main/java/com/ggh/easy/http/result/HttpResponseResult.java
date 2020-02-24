package com.ggh.easy.http.result;

/**
 * <p>Description:
 * <p>
 * <p>Created by Devin Sun on 2017/3/25.
 */

public class HttpResponseResult<T> {

    private static final int SUCCESS_STATUS = 0;

    private String err;
    private Integer code;
    private T data;

    public boolean isSuccess() {
        return code != null && code == SUCCESS_STATUS;
    }

    public String getMsg() {
        return err;
    }

    public Integer getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "HttpResponseResult{" +
                "err='" + err + '\'' +
                ", code=" + code +
                ", frameCallback=" + data +
                '}';
    }
}
