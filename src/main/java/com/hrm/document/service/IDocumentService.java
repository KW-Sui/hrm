package com.hrm.document.service;

import com.hrm.commons.beans.Document;
import com.hrm.utils.PageModel;

import java.util.List;

public interface IDocumentService {
    //获取符合查询条件的文件数
    int selectDocumentCount(String title);

    //查询符合查询条件的文件列表
    List<Document> selectDocument(String title, PageModel pageModel);

    //文档上传，向数据库添加文档信息
    int addDocument(Document document);

    //根据id删除文档信息
    int deleteDocument(Integer id);

    //根据id获取文件信息-文档删除与修改
    Document findDocumentById(Integer id);

    //修改数据库中该文档信息
    int updateDocument(Document document);
}
