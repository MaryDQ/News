package com.mlxphone.news.entity;

/**
 * Created by MLXPHONE on 2016/11/30 0030.
 */

public class NewsInfo {
    public String url;
    public String largeImageUrl;
    public String targetURL;
    public String title;
    public String type;

    public NewsInfo(String url, String largeImageUrl, String targetURL, String title, String type) {
        this.url = url;
        this.largeImageUrl = largeImageUrl;
        this.targetURL = targetURL;
        this.title = title;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public String getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
