package com.invoker.myapp.bean;

import java.util.List;

/**
 * Created by invoker on 2019-03-18
 * Description:  重写 数据返回处理
 * RxJava2 + Retrofit2
 */
public class MsgBean<T> {
    public String code;
    public String msg;
    public List<T> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
