package com.hrm.dept.dao;

import com.hrm.commons.beans.Dept;
import com.hrm.utils.PageModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptDao {
    //查询总记录数
    int findDeptCount(@Param("name")String name);

    //查询部门信息
    List<Dept> findDept(@Param("name") String name,@Param("pageModel") PageModel pageModel);

    //根据部门id查询部门信息，用于修改部门
    Dept findDeptById(Integer id);

    //修改部门信息
    int updateDept(Dept dept);

    //删除部门信息
    int deleteDept(@Param("ids") Integer[] ids);

    //添加部门信息
    int addDept(Dept dept);
}
