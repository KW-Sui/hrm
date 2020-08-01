package com.hrm.filter;

import com.hrm.commons.beans.User;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    /*不进行过滤的URL地址*/
    String[] ig_url = {"/index.jsp","/loginFrom.jsp","/login","/",".css",".js",".jpg"};
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        /*强制类型转换*/
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        /*获取本次请求的URL*/
        String requestURL = request.getRequestURI();

        for(String s :ig_url){
            /*如果本次请求是不进行过滤的请求*/
            if(requestURL.endsWith(s)){
                filterChain.doFilter(request,response);
                return ;
            }
        }
        /*获取用户登录信息*/
        User login_user = (User)request.getSession().getAttribute("login_user");

        /*用户已经登录*/
        if(login_user!=null){
            filterChain.doFilter(request,response);
            return ;
        } else {
            /*用户未登录*/
            request.setAttribute("massage","您还未登录，请登录后再进行访问");
            request.getRequestDispatcher("/index.jsp").forward(request,response);
            return;
        }
    }
}
