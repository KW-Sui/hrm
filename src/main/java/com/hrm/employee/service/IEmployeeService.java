package com.hrm.employee.service;

import com.hrm.commons.beans.Dept;
import com.hrm.commons.beans.Employee;
import com.hrm.commons.beans.Job;
import com.hrm.utils.PageModel;

import java.util.List;

public interface IEmployeeService {
    //获取dept的下拉列表框值
    List<Dept> FindDept();

    //获取job的下拉列表框
    List<Job> FindJob();

    //获取符合查询条件的员工信息
    List<Employee> FindEmployee(Employee employee, PageModel pageModel);

    //获取符合查询条件的员工数
    int selectEmployeeCount(Employee employee);

    //员工修改-通过id查询员工信息
    Employee findEmployeeById(int id);

    //员工修改-根据信息进行修改
    int modifyEmployee(Employee employee);

    //根据id删除员工信息
    int deleteEmployee(Integer[] ids);

    //添加员工信息
    int addEmployee(Employee employee);
}
