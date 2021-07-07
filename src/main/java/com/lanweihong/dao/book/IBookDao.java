package com.lanweihong.dao.book;

import com.lanweihong.dao.BaseMapper;
import com.lanweihong.entity.BookDO;
import org.apache.ibatis.annotations.Param;

/**
 * @author lanweihong 986310747@qq.com
 */
public interface IBookDao extends BaseMapper<BookDO> {

    /**
     * 通过名称查询
     * @param bookName 书名
     * @return
     */
    BookDO getByBookName(@Param("bookName") String bookName);
}
