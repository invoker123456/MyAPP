package com.invoker.myapp.bean;

/**
 * Created by invoker on 2019-03-27
 * Description:
 */
public class VideoTest {
    public String id;
    public String url;

    public VideoTest(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
