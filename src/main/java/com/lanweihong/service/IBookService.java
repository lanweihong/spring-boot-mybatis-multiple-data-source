package com.lanweihong.service;

import com.lanweihong.entity.BookDO;

import java.util.List;

/**
 * @author lanweihong 986310747@qq.com
 */
public interface IBookService {

    List<BookDO> listAll();

    BookDO getByBookName(String bookName);
}
