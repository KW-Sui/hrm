package com.hrm.job.handler;

import com.hrm.commons.beans.Job;
import com.hrm.job.service.JobService;
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
@RequestMapping("/job")
public class JobHandler {
    @Autowired
    private JobService jobService;

    //职位信息查询
    @RequestMapping("/selectJob")
    public String selectJob(@RequestParam(defaultValue = "1") Integer pageIndex, String name, Model model){
        PageModel pageModel = new PageModel();
        int count = jobService.selectJobCount(name);
        pageModel.setPageIndex(pageIndex);
        pageModel.setRecordCount(count);

        List<Job> jobs = jobService.selectJob(name,pageModel);
        /*//遍历测试
        for (Job job:jobs) {
            System.out.println("job的值是：---" + job);
        }*/
        model.addAttribute("name",name);
        model.addAttribute("jobs",jobs);
        model.addAttribute("pageModel",pageModel);
        return "/jsp/job/job.jsp";
    }

    //根据id查询职位信息
    @RequestMapping("/selectById")
    public String selectById(Integer id,Model model,Integer pageIndex){
        Job job = jobService.selectById(id);
        System.out.println("job的值是：---" + job + ",当前方法=JobHandler.selectById()");
        model.addAttribute("job",job);
        model.addAttribute("pageIndex",pageIndex);
        return "/jsp/job/showUpdateJob.jsp";
    }

    //根据id修改职位信息
    @RequestMapping("/updateJob")
    @ResponseBody
    public String update(Job job){
        System.out.println("job的值是：---" + job + ",当前方法=JobHandler.update()");
        int row = jobService.update(job);
        if(row>0){
            return "OK";
        }else {
            return "FAIL";
        }
    }

    //删除职位信息
    @RequestMapping("/deleteJob")
    @ResponseBody
    public String delete(Integer[] ids){
        try {
            int rows = jobService.deleteJob(ids);
            if(rows==ids.length){
                return "OK";
            }else{
                return "FAIL";
            }
        } catch (DataIntegrityViolationException e){
            return "ERROR";
        }
    }

    //添加职位信息
    @RequestMapping("/addJob")
    @ResponseBody
    public Object addJob(Job job){
        System.out.println("job的值是：---" + job);
        int row = jobService.addJob(job);
        if(row>0){
            PageModel pageModel = new PageModel();
            //获取总记录数，用来计算总页数
            pageModel.setRecordCount(jobService.selectJobCount(null));
            int pageIndex = pageModel.getTotalSize();
            return pageIndex;
        }else{
            return "FAIL";
        }
    }
}
