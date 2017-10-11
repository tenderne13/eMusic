package com.net.Entity;

public class Album {
    private String id;
    private String title;
    private String cover_img_url;
    private String source_url;
    private String author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_img_url() {
        return cover_img_url;
    }

    public void setCover_img_url(String cover_img_url) {
        this.cover_img_url = cover_img_url;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Album() {
    }

    public Album(String id, String title, String cover_img_url, String source_url, String author) {
        this.id = id;
        this.title = title;
        this.cover_img_url = cover_img_url;
        this.source_url = source_url;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", cover_img_url='" + cover_img_url + '\'' +
                ", source_url='" + source_url + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
