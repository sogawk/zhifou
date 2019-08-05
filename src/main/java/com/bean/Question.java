package com.bean;

import java.util.Date;

public class Question {
    private int id;
    private String title;
    private String content;
    private Date createTime;
    private int userId;
    private int commentCount;

    public Question() {
    }

    public Question(int id, String title, String content, Date createTime, int userId, int commentCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.userId = userId;
        this.commentCount = commentCount;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", userId=" + userId +
                ", commentCount=" + commentCount +
                '}';
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
