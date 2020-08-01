package com.hrm.user.service;

import com.hrm.utils.PageModel;
import com.hrm.commons.beans.User;
import com.hrm.user.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;
    @Override
    public User findByUser(User user) {
        return userDao.findByUserInfo(user);
    }

    @Override
    public int findUserCount(User user) {
        return userDao.findUserCount(user);
    }

    @Override
    public List<User> findUser(User user, PageModel pageModel) {
        Map map = new HashMap();
        map.put("user",user);
        map.put("pageModel",pageModel);
        return userDao.selectUser(map);
    }

    @Override
    public int addUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public User findUserById(Integer id) {
        return userDao.findUserById(id);
    }

    @Override
    public int modifyUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public int deleteUsers(Integer[] ids) {
        return userDao.deleteUsers(ids);
    }

    @Override
    public User findByLoginName(String loginName) {
        return userDao.findByLoginName(loginName);
    }
}
