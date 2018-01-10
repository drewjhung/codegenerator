package com.example.domain;

/**
 * Created by drew.jhung on 2017/6/17 16:36.
 */
/**
 * Product bean; note that it must be a public class!
 */
public class Product {

    private String url;
    private String name;

    // As per the JavaBeans spec., this defines the "url" bean property
    // It must be public!
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // As per the JavaBean spec., this defines the "name" bean property
    // It must be public!
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
