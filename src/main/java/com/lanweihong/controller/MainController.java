package com.lanweihong.controller;

import com.lanweihong.entity.BookDO;
import com.lanweihong.entity.UserDO;
import com.lanweihong.service.IBookService;
import com.lanweihong.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lanweihong 986310747@qq.com
 */
@RestController
@RequestMapping("/api/v1")
public class MainController {

    private final IUserService userService;
    private final IBookService bookService;

    @Autowired
    public MainController(IUserService userService, IBookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public Map<String, Object> queryBooks(@RequestParam(value = "name", required = false) String bookName) {
        List<BookDO> books = new ArrayList<>();
        if (StringUtil.isEmpty(bookName)) {
            books = this.bookService.listAll();
        } else {
            BookDO book = bookService.getByBookName(bookName);
            books.add(book);
        }
        Map<String, Object> result = new HashMap<>(1);
        result.put("data", books);
        return result;
    }

    @GetMapping("/users")
    public Map<String, Object> queryUsers(@RequestParam(value = "name", required = false) String userName) {
        List<UserDO> users = new ArrayList<>();
        if (StringUtil.isEmpty(userName)) {
            users = this.userService.listAll();
        } else {
            UserDO user = userService.getByUserName(userName);
            users.add(user);
        }

        Map<String, Object> result = new HashMap<>(1);
        result.put("data", users);
        return result;
    }
}
