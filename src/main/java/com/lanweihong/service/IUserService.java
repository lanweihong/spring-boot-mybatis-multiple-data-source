package com.lanweihong.service;

import com.lanweihong.entity.UserDO;

import java.util.List;

/**
 * @author lanweihong 986310747@qq.com
 */
public interface IUserService {

    List<UserDO> listAll();

    UserDO getByUserName(String userName);
}
