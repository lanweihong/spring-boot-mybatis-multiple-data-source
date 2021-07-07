package com.lanweihong.dao.user;

import com.lanweihong.dao.BaseMapper;
import com.lanweihong.entity.UserDO;
import org.apache.ibatis.annotations.Param;

/**
 * @author lanweihong 986310747@qq.com
 */
public interface IUserDao extends BaseMapper<UserDO> {

    /**
     * 根据用户名查询
     * @param userName 用户名
     * @return
     */
    UserDO getByUserName(@Param("userName") String userName);
}
