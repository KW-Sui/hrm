package com.hrm.employee.service;

import com.hrm.commons.beans.Dept;
import com.hrm.commons.beans.Employee;
import com.hrm.commons.beans.Job;
import com.hrm.employee.dao.IEmployeeDao;
import com.hrm.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    IEmployeeDao employeeDao;
    //获取dept的下拉列表框
    @Override
    public List<Dept> FindDept() {
        return employeeDao.selectDept();
    }

    //获取dept的下拉列表框
    @Override
    public List<Job> FindJob() {
        return employeeDao.selectJob();
    }

    //获取符合查询条件的员工信息
    @Override
    public List<Employee> FindEmployee(Employee employee, PageModel pageModel) {
        return employeeDao.selectEmployee(employee,pageModel);
    }

    //获取符合查询条件的员工数
    @Override
    public int selectEmployeeCount(Employee employee) {
        return employeeDao.selectEmployeeCount(employee);
    }

    //员工修改-通过id查询员工信息
    @Override
    public Employee findEmployeeById(int id) {
        return employeeDao.findEmployeeById(id);
    }

    //员工修改-根据信息进行修改
    @Override
    public int modifyEmployee(Employee employee) {
        return employeeDao.updateEmployee(employee);
    }

    //根据id删除员工信息
    @Override
    public int deleteEmployee(Integer[] ids) {
        return employeeDao.deleteEmployee(ids);
    }

    //添加员工信息
    @Override
    public int addEmployee(Employee employee) {
        return employeeDao.insertEmployee(employee);
    }
}
