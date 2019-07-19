package com.invoker.myapp.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by invoker on 2019-03-08
 * Description: 单次返回结果的数据结构：
 * http://gank.io/api/data/福利/10/1
 */
public class NewsEntity {

    private boolean error;
    private List<NewsResultEntity> results = new ArrayList<>();

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<NewsResultEntity> getResults() {
        return results;
    }

    public void setResults(List<NewsResultEntity> results) {
        this.results = results;
    }
}
