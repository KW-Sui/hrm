package com.hrm.notice.dao;

import com.hrm.commons.beans.Notice;
import com.hrm.utils.PageModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface INoticeDao {

    //获取符合查询条件的公告数
    int selectNoticeCount(Notice notice);

    //获取符合查询条件的公告列表，分页查询
    List<Notice> selectNotice(@Param("notice") Notice notice, @Param("pageModel") PageModel pageModel);

    //添加公告，用户为当前登录用户
    int addNotice(Notice notice);

    //根据ids删除公告
    int deleteNotice(Integer[] ids);

    //根据id获取公告信息-用于公告修改和预览
    Notice findNoticeById(Integer id);

    //公告修改
    int updateNotice(Notice notice);
}
