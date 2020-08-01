package com.hrm.employee.dao;

import com.hrm.commons.beans.Dept;
import com.hrm.commons.beans.Employee;
import com.hrm.commons.beans.Job;
import com.hrm.utils.PageModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IEmployeeDao {
    //获取dept的下拉列表框
    List<Dept> selectDept();

    //获取dept的下拉列表框
    List<Job> selectJob();

    //获取符合查询条件的员工信息
    List<Employee> selectEmployee(@Param("employee") Employee employee, @Param("pageModel") PageModel pageModel);

    //获取符合查询条件的员工数
    int selectEmployeeCount(Employee employee);

    //员工修改-通过id查询员工信息
    Employee findEmployeeById(int id);

    //员工修改-根据信息进行修改
    int updateEmployee(Employee employee);

    //根据id删除员工信息
    int deleteEmployee(@Param("ids") Integer[] ids);

    //添加员工信息
    int insertEmployee(Employee employee);
}
