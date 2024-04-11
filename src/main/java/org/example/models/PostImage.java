package org.example.models;

import java.io.InputStream;

public class PostImage {
    private Integer id;
    private String url;
    private Post post;

    private InputStream image_blob;

    public PostImage(){

    }
    public PostImage(Integer id, Post post,InputStream image_blob) {
        this.id = id;
        this.url = null;
        this.post = post;
        this.image_blob = image_blob ;
    }
    public PostImage(Post post,InputStream image_blob) {
        this.url = null;
        this.post = post;
        this.image_blob = image_blob ;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public InputStream getImage_blob() {
        return image_blob;
    }

    public void setImage_blob(InputStream image_blob) {
        this.image_blob = image_blob;
    }

    @Override
    public String toString() {
        return "PostImage{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", post=" + post +
                ", image_blob=" + image_blob +
                '}';
    }
}
