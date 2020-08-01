package com.hrm.job.service;

import com.hrm.commons.beans.Job;
import com.hrm.job.dao.JobDao;
import com.hrm.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobDao jobDao;
    //查询职位总记录数
    @Override
    public int selectJobCount(String name) {
        return jobDao.selectJobCount(name);
    }

    //通过职位名查询职位信息
    @Override
    public List<Job> selectJob(String name, PageModel pageModel) {
        return jobDao.selectJob(name,pageModel);
    }

    //通过id查找职位信息
    @Override
    public Job selectById(Integer id) {
        return jobDao.selectById(id);
    }

    //修改职位信息
    @Override
    public int update(Job job) {
        return jobDao.updateJob(job);
    }

    //添加职位信息
    @Override
    public int addJob(Job job) {
        return jobDao.addJob(job);
    }

    //删除职位信息
    @Override
    public int deleteJob(Integer[] ids) {
        return jobDao.deleteJob(ids);
    }
}
