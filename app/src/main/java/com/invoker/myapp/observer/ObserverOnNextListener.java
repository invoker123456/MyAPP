package com.invoker.myapp.observer;

/**
 * Created by invoker on 2019-03-18
 * Description: 新建监听接口
 * 使用通配泛型，适用于所有类型的数据。
 */
public interface ObserverOnNextListener<T> {
    void onNext(T t);
}
