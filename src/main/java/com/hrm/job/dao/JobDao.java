package com.hrm.job.dao;

import com.hrm.commons.beans.Job;
import com.hrm.utils.PageModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface JobDao {
    //查询职位总记录数
    @SelectProvider(type = JobProvider.class,method = "selectJobCount")
    int selectJobCount(@Param("name") String name);

    //通过职位名查询职位信息
    @SelectProvider(type = JobProvider.class,method = "selectJob")
    List<Job> selectJob(@Param("name") String name, @Param("pageModel") PageModel pageModel);

    //通过id查找职位信息
    @Select("select * from job_inf where id = #{id}")
    Job selectById(Integer id);

    //修改职位信息
    @Update("update job_inf set name=#{name},remark=#{remark} where id=#{id}")
    int updateJob(Job job);

    //添加职位信息
    @Insert("insert into job_inf(name,remark) values(#{name},#{remark})")
    //@InsertProvider(type = JobProvider.class,method = "addJob")
    int addJob(Job job);

    //删除职位信息
    @DeleteProvider(type = JobProvider.class, method = "deleteJob")
    int deleteJob(@Param("ids") Integer[] ids);
}
