package com.hrm.notice.handler;

import com.hrm.commons.beans.Notice;
import com.hrm.notice.service.INoticeService;
import com.hrm.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeHandler {
    @Autowired
    private INoticeService inoticeService;

    @RequestMapping("/selectNotice")
    public String selectNotice(@RequestParam(defaultValue = "1") Integer pageIndex, Model model, Notice notice){
        //分页设置
        PageModel pageModel = new PageModel();
        pageModel.setPageIndex(pageIndex);
        int count = inoticeService.selectNoticeCount(notice);
        pageModel.setRecordCount(count);

        List<Notice> notices = inoticeService.selectNotice(notice,pageModel);
        for(Notice n:notices){
            System.out.println("n的值是：---" + n);
        }
        model.addAttribute("notice",notice);
        model.addAttribute("notices",notices);
        model.addAttribute("pageModel",pageModel);
        return "/jsp/notice/notice.jsp";
    }

    //根据id获取公告信息-用于公告修改
    @RequestMapping("/findNoticeById")
    public String findNoticeById(Integer id,Model model,Integer pageIndex){
        Notice notice = inoticeService.findNoticeById(id);
        //pageIndex由于返回之前的页面
        model.addAttribute("pageIndex",pageIndex);
        model.addAttribute("notice",notice);
        return "/jsp/notice/showUpdateNotice.jsp";
    }

    //公告修改
    @RequestMapping("/updateNotice")
    @ResponseBody
    public String updateNotice(Notice notice){
        int row = inoticeService.updateNotice(notice);
        if(row>0){
            return "OK";
        }else {
            return "FAIL";
        }
    }

    //公告内容预览
    @RequestMapping("/previewNotice")
    public String previewNotice(Integer id,Model model){
        Notice notice = inoticeService.findNoticeById(id);
        model.addAttribute("notice",notice);
        return "/jsp/notice/previewNotice.jsp";
    }

    //根据ids删除公告
    @RequestMapping("/removeNotice")
    @ResponseBody
    public String removeNotice(Integer[] ids){
        int rows = inoticeService.removeNotice(ids);
        if(rows==ids.length){
            return "OK";
        }else{
            return "FAIL";
        }
    }

    //添加公告，用户未为当前登录用户
    @RequestMapping("/addNotice")
    @ResponseBody
    public String addNotice(Notice notice,Model model){
        int row = inoticeService.addNotice(notice);
        if(row>0){
            //获取新添加数据所在的页码
            PageModel pageModel = new PageModel();
            pageModel.setRecordCount(inoticeService.selectNoticeCount(null));
            int index = pageModel.getTotalSize();
            model.addAttribute("index",index);
            return "OK";
        }else{
            return "FAIL";
        }
    }
}
