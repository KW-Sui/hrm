package com.hrm.job.dao;

import com.hrm.commons.beans.Job;
import com.hrm.utils.PageModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class JobProvider {

    //查询职位信息，返回String类型的SQL语句
    //参数前加final
    public String selectJob(final @Param("name") String name, @Param("pageModel") PageModel pageModel){
        String sql = new SQL(){
            {
                this.SELECT("*");
                this.FROM("job_inf");
                if(name != null && !"".equals(name)){
                    this.WHERE("name like '%' #{name} '%'");
                }
                this.LIMIT("#{pageModel.firstLimitParam},#{pageModel.pageSize}");
            }
        }.toString();
        return sql;
    }

    //查询职位总记录数
    public String selectJobCount(final @Param("name")String name){
        String sql = new SQL(){
            {
                this.SELECT("count(*)");
                this.FROM("job_inf");
                if(name != null && !"".equals(name)){
                    this.WHERE("name like '%' #{name} '%'");
                }
            }
        }.toString();
        return sql;
    }

    //删除职位信息
    public String deleteJob(Map map){
        Integer[] ids = (Integer[]) map.get("ids");
        StringBuffer sql = new StringBuffer();
        sql.append("delete from job_inf");
        sql.append(" where id in(");
        for (Integer id:ids) {
            sql.append(id+",");
        }
        //删除最后一个符号,
        sql.deleteCharAt(sql.length()-1);
        sql.append(")");
        return sql.toString();
    }

    //添加部门信息
    public String addJob(final Job job){
        return new SQL(){
            {
                this.INSERT_INTO("job_inf");
                if(job.getName()!= null && !"".equals(job.getName())){
                    this.VALUES("name","#{name}");
                }
                if(job.getRemark()!= null && !"".equals(job.getRemark())){
                    this.VALUES("remark","#{remark}");
                }
            }
        }.toString();
    }
}
