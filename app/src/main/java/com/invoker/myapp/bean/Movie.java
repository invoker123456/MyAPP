package com.invoker.myapp.bean;

import java.util.List;

/**
 * Created by invoker on 2019-03-18
 * Description:  豆瓣电影 top 250
 * https://api.douban.com/v2/movie/top250?start=0&count=10
 */
public class Movie {
    private String title;
    private List<Subjects> subjects;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Subjects> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subjects> subjects) {
        this.subjects = subjects;
    }
}
