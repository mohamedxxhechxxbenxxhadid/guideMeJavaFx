package org.example.models;

public class Vote {
    private Integer id;
    private Boolean up;
    private Boolean down;
    private Post post;
    private String user;

    public Vote(){

    }
    public Vote(Boolean up, Boolean down, Post post, String user) {
        this.up = up;
        this.down = down;
        this.post = post;
        this.user = user;
    }
    public Vote(int id,Boolean up, Boolean down, Post post, String user) {
        this.id = id ;
        this.up = up;
        this.down = down;
        this.post = post;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getUp() {
        return up;
    }

    public void setUp(Boolean up) {
        this.up = up;
    }

    public Boolean getDown() {
        return down;
    }

    public void setDown(Boolean down) {
        this.down = down;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", up=" + up +
                ", down=" + down +
                ", post=" + post +
                ", user='" + user + '\'' +
                '}';
    }
}
