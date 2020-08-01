package com.hrm.employee.handler;

import com.hrm.commons.beans.Dept;
import com.hrm.commons.beans.Employee;
import com.hrm.commons.beans.Job;
import com.hrm.employee.service.IEmployeeService;
import com.hrm.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeHandler {
    @Autowired
    IEmployeeService employeeService;

    //显示员工信息列表
    @RequestMapping("/selectEmployee")
    public String selectEmployee(@RequestParam(defaultValue = "1") Integer pageIndex
            ,Integer dept_id,Integer job_id, Employee employee, Model model){
        //对employee中的dept和job赋值
        if(dept_id!=null){
            Dept dept = new Dept();
            dept.setId(dept_id);
            employee.setDept(dept);
        }
        if(job_id!=null){
            Job job = new Job();
            job.setId(job_id);
            employee.setJob(job);
        }
        //分页查询属性配置
        PageModel pageModel = new PageModel();
        int count = employeeService.selectEmployeeCount(employee);
        pageModel.setPageIndex(pageIndex);
        pageModel.setRecordCount(count);

        //部门下拉列表信息
        List<Dept> depts = employeeService.FindDept();
        //职位下拉列表信息
        List<Job> jobs = employeeService.FindJob();

        List<Employee> employees = employeeService.FindEmployee(employee,pageModel);
        model.addAttribute("depts",depts);
        model.addAttribute("jobs",jobs);
        model.addAttribute("employees",employees);
        model.addAttribute("employee",employee);
        model.addAttribute("pageModel",pageModel);
        return "/jsp/employee/employee.jsp";
    }

    //员工修改-通过id查询员工信息
    @RequestMapping("/findEmployeeById")
    public String findEmployee(int id,Model model,Integer pageIndex){
        //获取职位与部门下拉列表框的值
        List<Job> jobs = employeeService.FindJob();
        List<Dept> depts = employeeService.FindDept();
        Employee employee = employeeService.findEmployeeById(id);
        System.out.println("employee的值是：---" + employee);
        model.addAttribute("jobs",jobs);
        model.addAttribute("depts",depts);
        model.addAttribute("employee",employee);
        model.addAttribute("pageIndex",pageIndex);
        return "/jsp/employee/showUpdateEmployee.jsp";
    }

    //员工修改-根据信息进行修改
    @RequestMapping("/modifyEmployee")
    @ResponseBody
    public String modifyEmployee(Employee employee){
        System.out.println("employee的值是：---" + employee);
        int row = employeeService.modifyEmployee(employee);
        if(row>0){
            return "OK";
        }else{
            return "FAIL";
        }
    }

    //员工删除
    @RequestMapping("/deleteEmployee")
    @ResponseBody
    public String deleteEmployee(Integer[] ids){
        int rows = employeeService.deleteEmployee(ids);
        if(ids.length==rows){
            return "OK";
        }else {
            return "FAIL";
        }
    }

    //为员工添加页面提供dept和job的下拉列表框
    @RequestMapping("/toAddEmployee")
    public String toAddEmployee(Model model){
        List<Job> jobs = employeeService.FindJob();
        List<Dept> depts = employeeService.FindDept();
        model.addAttribute("jobs",jobs);
        model.addAttribute("depts",depts);
        return "/jsp/employee/showAddEmployee.jsp";
    }

    //添加员工操作
    @RequestMapping("/addEmployee")
    @ResponseBody
    public Object addEmployee(Employee employee){
        //添加信息操作
        int row = employeeService.addEmployee(employee);
        if(row>0){
            //提供页面页码（最后页）
            PageModel pageModel = new PageModel();
            pageModel.setRecordCount(employeeService.selectEmployeeCount(null));
            int pageIndex = pageModel.getTotalSize();
            return pageIndex;
        }else {
            return "FAIL";
        }
    }
}
