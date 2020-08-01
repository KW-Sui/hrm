package com.hrm.notice.service;

import com.hrm.commons.beans.Notice;
import com.hrm.notice.dao.INoticeDao;
import com.hrm.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoticeServiceImpl implements INoticeService {
    @Autowired
    private INoticeDao iNoticeDao;

    //获取符合查询条件的公告数
    @Override
    public int selectNoticeCount(Notice notice) {
        return iNoticeDao.selectNoticeCount(notice);
    }

    //获取符合查询条件的公告列表，分页查询
    @Override
    public List<Notice> selectNotice(Notice notice, PageModel pageModel) {
        return iNoticeDao.selectNotice(notice,pageModel);
    }

    //添加公告，用户为当前登录用户
    @Override
    public int addNotice(Notice notice) {
        return iNoticeDao.addNotice(notice);
    }

    //根据ids删除公告
    @Override
    public int removeNotice(Integer[] ids) {
        return iNoticeDao.deleteNotice(ids);
    }

    //根据id获取公告信息-用于公告修改和预览
    @Override
    public Notice findNoticeById(Integer id) {
        return iNoticeDao.findNoticeById(id);
    }

    //公告修改
    @Override
    public int updateNotice(Notice notice) {
        return iNoticeDao.updateNotice(notice);
    }
}
