package com.hrm.dept.handler;

import com.hrm.commons.beans.Dept;
import com.hrm.dept.service.DeptService;
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
@RequestMapping("/dept")
public class DeptHandler {
    @Autowired
    private DeptService deptService;

    //查询部门信息
    @RequestMapping("/selectDept")
    public String findDept(@RequestParam(defaultValue = "1") Integer pageIndex, String name, Model model){
        int count = deptService.findDeptCount(name);
        PageModel pageModel = new PageModel();
        pageModel.setPageIndex(pageIndex);
        pageModel.setRecordCount(count);

        List<Dept> depts = deptService.findDept(name,pageModel);
        model.addAttribute("depts",depts);
        model.addAttribute("pageModel",pageModel);
        model.addAttribute("name",name);
        return "/jsp/dept/dept.jsp";
    }

    //根据部门id查询部门信息，用于修改部门
    @RequestMapping("/findDeptById")
    public String findDeptById(Integer id,Integer pageIndex,Model model){
        Dept dept = deptService.findDeptById(id);
        model.addAttribute("dept",dept);
        model.addAttribute("pageIndex",pageIndex);
        System.out.println("pageIndex的值是：---" + pageIndex);
        return "/jsp/dept/showUpdateDept.jsp";
    }

    //修改部门信息
    @RequestMapping("/updateDept")
    @ResponseBody
    public String updateDept(Dept dept){
        System.out.println("dept的值是：---" + dept);
        int rows = deptService.updateDept(dept);
        System.out.println("rows的值是：---" + rows);
        if(rows>0){
            return "OK";
        }else {
            return "NO";
        }
    }

    //删除部门信息
    @RequestMapping("/deleteDept")
    @ResponseBody
    public String deleteDept(Integer[] ids){
        try{
            int rows = deptService.deleteDept(ids);
            if(rows==ids.length){
                return "OK";
            }else{
                return "FAIL";
            }
        } catch (DataIntegrityViolationException e){
            return "ERROR";
        }
    }

    //添加部门信息
    @RequestMapping("/addDept")
    @ResponseBody
    public Object addDept(Dept dept,Model model){
        int rows = deptService.addDept(dept);
        if(rows>0){
            PageModel pageModel = new PageModel();
            pageModel.setRecordCount(deptService.findDeptCount(null));
            //获取部门信息总页数
            int index = pageModel.getTotalSize();
            return index;
        }else{
            return "FAIL";
        }
    }
}
