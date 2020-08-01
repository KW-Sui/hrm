package com.hrm.user.dao;

import com.hrm.commons.beans.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IUserDao {
    //用户登录-用于判断用户信息
    User findByUserInfo(User user);

    //添加用户
    int insertUser(User user);

    //查询符合查询的用户数-用于分页
    int findUserCount(User user);

    //查询符合条件的用户信息-分页查询
    List<User> selectUser(Map map);

    //根据id获取用户信息-用于修改用户信息
    User findUserById(Integer id);

    //修改用户信息
    int updateUser(User user);

    //删除用户信息
    int deleteUsers(@Param("ids") Integer[] ids);

    //用于判断新添加的用户名是否重复
    User findByLoginName(String loginName);
}
