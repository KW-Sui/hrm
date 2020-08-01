package com.hrm.user.handler;

import com.hrm.commons.beans.User;
import com.hrm.user.service.IUserService;
import com.hrm.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserHandler {
    @Autowired
    private IUserService userService;

    //用户登录
    @RequestMapping("/login")
    public String login(User user, HttpSession session, Model model){
        User login_user = userService.findByUser(user);
        if(login_user!=null){
            //将用户信息保存入session域对象中
            session.setAttribute("login_user",login_user);
            return "/jsp/main.jsp";
        }else{
            model.addAttribute("massage","用户名或密码错误，请重新登录");
            return "/index.jsp";
        }
    }

    //退出登录
    @RequestMapping("/logout")
    public String logout(HttpSession session,Model model){
        //将login_user信息从session中已移除，结合过滤器退出页面
        session.removeAttribute("login_user");
        model.addAttribute("massage","退出成功，可重新登录！");
        return "/index.jsp";
    }

    //查找用户，使用动态SQL
    @RequestMapping("/findUser")
    public String findUser(@RequestParam(defaultValue = "1") Integer pageIndex, User user, Model model){
        //查找符合条件的总记录数
        int count = userService.findUserCount(user);
        PageModel pageModel = new PageModel();
        pageModel.setPageIndex(pageIndex);
        pageModel.setRecordCount(count);

        List<User> users = userService.findUser(user,pageModel);
        /*for(User u:users){
            System.out.println("u的值是：---" + u );
        }*/
        model.addAttribute("user",user);
        model.addAttribute("users",users);
        model.addAttribute("pageModel",pageModel);
        return "/jsp/user/user.jsp";
    }

    //修改用户信息
    @RequestMapping("/modifyUser")
    public String modifyUser(User user,String flag,Model model,Integer pageIndex){
        //flag作为是否将原用户信息放入页面中的标志
        if(flag==null){
            user = userService.findUserById(user.getId());
            model.addAttribute("user",user);
            model.addAttribute("pageIndex",pageIndex);
            return "/jsp/user/showUpdateUser.jsp";
        }else {
            int row = userService.modifyUser(user);
            if(row>0){
                return "redirect:/user/findUser?pageIndex="+pageIndex;
            }else{
                model.addAttribute("fail","用户修改信息失败！");
                return "/jsp/fail.jsp";
            }
        }
    }

    //添加用户
    @RequestMapping("/addUser")
    public String addUser(User user,Model model){
        Date date = new Date();
        user.setCreateDate(date);
        int row = userService.addUser(user);
        if(row>0){
            PageModel pageModel = new PageModel();
            int count = userService.findUserCount(null);
            pageModel.setRecordCount(count);
            //pageIndex为最后一页,直接显示新添加的用户信息,redirect重定向
            return "redirect:/user/findUser?pageIndex="+pageModel.getTotalSize();
        }else{
            model.addAttribute("file","添加用户失败！");
        }
        return "/jsp/main.jsp";
    }

    //删除用户
    @RequestMapping("/deleteUser")
    public String deleteUser(Integer[] ids,Model model,HttpSession httpSession){
        User login_user = (User) httpSession.getAttribute("login_user");
        //判断所删用户是否包含当前登录用户
        for (Integer id: ids) {
            if(id==login_user.getId()){
                model.addAttribute("fail","不能删除当前登录用户！");
                return "/jsp/fail.jsp";
            }
        }

        //防止因删除而导致的数据完整性异常
        try{
            int rows = userService.deleteUsers(ids);
            if(rows==ids.length){
                return "/user/findUser";
            }else{
                model.addAttribute("fail","删除用户失败！");
                return "/jsp/fail/jsp";
            }
        } catch (DataIntegrityViolationException e){
            model.addAttribute("fail","所删除用户尚有发布的公告，请将公告删除后再删除用户！");
            return "/jsp/fail.jsp";
        }
    }

    //检测用户名是否重复
    @RequestMapping("/checkLoginName")
    @ResponseBody
    public String checkLoginName(String loginName){
        User user = userService.findByLoginName(loginName);
        if(user!= null){
            return "EXIST";
        }else {
            return "OK";
        }
    }
}
