package com.hrm.user.service;

import com.hrm.utils.PageModel;
import com.hrm.commons.beans.User;

import java.util.List;

public interface IUserService {
    //用户登录-用于判断用户信息
    User findByUser(User user);

    //添加用户
    int addUser(User user);

    //查询符合查询的用户数-用于分页
    int findUserCount(User user);

    //查询符合条件的用户信息-分页查询
    List<User> findUser(User user, PageModel pageModel);

    //根据id获取用户信息-用于修改用户信息
    User findUserById(Integer id);

    //修改用户信息
    int modifyUser(User user);

    //删除用户信息
    int deleteUsers(Integer[] ids);

    //用于判断新添加的用户名是否重复
    User findByLoginName(String loginName);
}
