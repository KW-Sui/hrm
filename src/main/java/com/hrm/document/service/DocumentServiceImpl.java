package com.hrm.document.service;

import com.hrm.commons.beans.Document;
import com.hrm.document.dao.IDocumentDao;
import com.hrm.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DocumentServiceImpl implements IDocumentService {
    @Autowired
    IDocumentDao documentDao;
    //获取符合查询条件的文件数
    @Override
    public int selectDocumentCount(String title) {
        return documentDao.selectDocumentCount(title);
    }

    //查询符合查询条件的文件列表
    @Override
    public List<Document> selectDocument(String title, PageModel pageModel) {
        return documentDao.selectDocument(title,pageModel);
    }

    //文档上传，向数据库添加文档信息
    @Override
    public int addDocument(Document document) {
        return documentDao.addDocument(document);
    }

    //根据id删除文档信息
    @Override
    public int deleteDocument(Integer id) {
        return documentDao.deleteDocument(id);
    }

    //根据id获取文件信息-文档删除与修改
    @Override
    public Document findDocumentById(Integer id) {
        return documentDao.findDocumentById(id);
    }

    //修改数据库中该文档信息
    @Override
    public int updateDocument(Document document) {
        return documentDao.updateDocument(document);
    }
}
