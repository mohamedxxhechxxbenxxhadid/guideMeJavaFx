package org.example.models;

import java.time.LocalDateTime;
import java.util.Collection;

public class Post {
    private int id;
    private String title;
    private String description;
    private Post post;
    private Collection<Post> comments;
    private Collection<PostImage> postImages;
    private Boolean approved;
    private Boolean bigPost;
    private Integer upVoteNum;
    private Integer downVoteNum;
    private LocalDateTime createdAt;
    private User creator;
    public Post(){

    }
    public Post(String title, String description, Post post,
                Boolean approved,
                Boolean bigPost, Integer upVoteNum, Integer downVoteNum,  User creator) {
        this.title = title;
        this.description = description;
        this.post = post;
        this.approved = approved;
        this.bigPost = bigPost;
        this.upVoteNum = upVoteNum;
        this.downVoteNum = downVoteNum;
        this.createdAt =LocalDateTime.now();;
        this.creator = creator;
    }
    public Post(String title, String description, Post post, Collection<Post> comments,
                 Boolean approved,
                Boolean bigPost, Integer upVoteNum, Integer downVoteNum, LocalDateTime  createdAt, User creator) {
        this.title = title;
        this.description = description;
        this.post = post;
        this.comments = comments;
        this.approved = approved;
        this.bigPost = bigPost;
        this.upVoteNum = upVoteNum;
        this.downVoteNum = downVoteNum;
        this.createdAt = createdAt;
        this.creator = creator;
    }
    public Post(int id,String title, String description, Collection<Post> comments,
                Boolean approved,
                Boolean bigPost, Integer upVoteNum, Integer downVoteNum, LocalDateTime  createdAt, User creator) {
        this.id = id ;
        this.title = title;
        this.description = description;
        this.comments = comments;
        this.approved = approved;
        this.bigPost = bigPost;
        this.upVoteNum = upVoteNum;
        this.downVoteNum = downVoteNum;
        this.createdAt = createdAt;
        this.creator = creator;
    }
    public Post(int id,String title, String description,Post post, Collection<Post> comments,
                Boolean approved,
                Boolean bigPost, Integer upVoteNum, Integer downVoteNum, LocalDateTime  createdAt, User creator) {
        this.id = id ;
        this.title = title;
        this.description = description;
        this.comments = comments;
        this.post = post;
        this.approved = approved;
        this.bigPost = bigPost;
        this.upVoteNum = upVoteNum;
        this.downVoteNum = downVoteNum;
        this.createdAt = createdAt;
        this.creator = creator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Collection<Post> getComments() {
        return comments;
    }

    public void setComments(Collection<Post> comments) {
        this.comments = comments;
    }


    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }


    public Boolean getBigPost() {
        return bigPost;
    }

    public void setBigPost(Boolean bigPost) {
        this.bigPost = bigPost;
    }

    public Integer getUpVoteNum() {
        return upVoteNum;
    }

    public void setUpVoteNum(Integer upVoteNum) {
        this.upVoteNum = upVoteNum;
    }

    public Integer getDownVoteNum() {
        return downVoteNum;
    }

    public void setDownVoteNum(Integer downVoteNum) {
        this.downVoteNum = downVoteNum;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Collection<PostImage> getPostImages() {
        return postImages;
    }

    public void setPostImages(Collection<PostImage> postImages) {
        this.postImages = postImages;
    }

    @Override
    public String toString() {

        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", post=" + (post != null ? post.getId() : "null") +
                ", comments=" + comments +
                ", postImages=" + postImages +
                ", approved=" + approved +
                ", bigPost=" + bigPost +
                ", upVoteNum=" + upVoteNum +
                ", downVoteNum=" + downVoteNum +
                ", createdAt=" + createdAt +
                ", creator=" + creator +
                '}';
    }
}

