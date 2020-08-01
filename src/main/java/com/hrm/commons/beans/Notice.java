package com.hrm.commons.beans;

import java.util.Date;

public class Notice {
    private Integer id;
    private String title;
    private String content;
    private Date create_date;
    private User user;

    public Notice() {
    }

    public Notice(Integer id, String title, String content, Date create_date, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.create_date = create_date;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", create_date=" + create_date +
                ", user=" + user +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
