package com.hrm.commons.beans;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class Document {
    private Integer id;
    private String title;
    private String fileName;
    //用来接收前端传来的file文件，类型为MultipartFile
    private MultipartFile file;
    private Date create_date;
    //关联属性
    private User user;
    private String remark;

    public Document() {
    }

    public Document(Integer id, String title, String fileName, MultipartFile file, Date create_date, User user, String remark) {
        this.id = id;
        this.title = title;
        this.fileName = fileName;
        this.file = file;
        this.create_date = create_date;
        this.user = user;
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", fileName='" + fileName + '\'' +
                ", file=" + file +
                ", create_date=" + create_date +
                ", user=" + user +
                ", remark='" + remark + '\'' +
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
