package com.lanweihong.service.impl;

import com.lanweihong.dao.user.IUserDao;
import com.lanweihong.entity.UserDO;
import com.lanweihong.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lanweihong 986310747@qq.com
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    private final IUserDao userDao;

    @Autowired
    public UserServiceImpl(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<UserDO> listAll() {
        return userDao.selectAll();
    }

    @Override
    public UserDO getByUserName(String userName) {
        return userDao.getByUserName(userName);
    }
}
