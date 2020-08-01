package com.hrm.dept.service;

import com.hrm.commons.beans.Dept;
import com.hrm.dept.dao.DeptDao;
import com.hrm.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;
    //查询总记录数
    @Override
    public int findDeptCount(String name) {
        return deptDao.findDeptCount(name);
    }

    //查询部门信息
    @Override
    public List<Dept> findDept(String name, PageModel pageModel) {
        return deptDao.findDept(name,pageModel);
    }

    //根据部门id查询部门信息，用于修改部门
    @Override
    public Dept findDeptById(Integer id) {
        return deptDao.findDeptById(id);
    }

    //修改部门信息
    @Override
    public int updateDept(Dept dept) {
        return deptDao.updateDept(dept);
    }

    //删除部门信息
    @Override
    public int deleteDept(Integer[] ids) {
        return deptDao.deleteDept(ids);
    }

    //添加部门信息
    @Override
    public int addDept(Dept dept) {
        return deptDao.addDept(dept);
    }
}
