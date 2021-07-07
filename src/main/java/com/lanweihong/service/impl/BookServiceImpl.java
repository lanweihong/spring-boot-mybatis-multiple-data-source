package com.lanweihong.service.impl;

import com.lanweihong.dao.book.IBookDao;
import com.lanweihong.entity.BookDO;
import com.lanweihong.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lanweihong 986310747@qq.com
 */
@Service("bookService")
public class BookServiceImpl implements IBookService {

    private final IBookDao bookDao;

    @Autowired
    public BookServiceImpl(IBookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<BookDO> listAll() {
        return bookDao.selectAll();
    }

    @Override
    public BookDO getByBookName(String bookName) {
        return bookDao.getByBookName(bookName);
    }
}
