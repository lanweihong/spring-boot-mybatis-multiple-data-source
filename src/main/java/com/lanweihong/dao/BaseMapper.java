package com.lanweihong.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author lanweihong 986310747@qq.com
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
