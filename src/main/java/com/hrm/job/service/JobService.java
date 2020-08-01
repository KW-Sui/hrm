package com.hrm.job.service;

import com.hrm.commons.beans.Job;
import com.hrm.utils.PageModel;

import java.util.List;

public interface JobService {
    //查询职位总记录数
    int selectJobCount(String name);

    //通过职位名查询职位信息
    List<Job> selectJob(String name, PageModel pageModel);

    //通过id查找职位信息
    Job selectById(Integer id);

    //修改职位信息
    int update(Job job);

    //添加职位信息
    int addJob(Job job);

    //删除职位信息
    int deleteJob(Integer[] ids);
}
