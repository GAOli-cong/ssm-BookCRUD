package com.glc.controller;

import com.glc.pejo.Books;
import com.glc.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    //controller 调用service

    @Autowired
    @Qualifier("BookServiceImpl")
    private BookService bookService;


    //查询全部的书籍，并且返回到一个书籍展示
    @RequestMapping("allBook")
    public String list(Model model) {
        List<Books> list = bookService.queryAllBook();

        model.addAttribute("list", list);
        return "allBook";
    }

    //跳转到增加书籍页面
    @RequestMapping("/toAddBook")
    public String toAddPager() {
        return "addBook";
    }

    //添加书籍的请求
    @RequestMapping("/addBook")
    public String addBook(Books books) {
        System.out.println("addBook___" + books);
        bookService.addBook(books);
        return "redirect:/book/allBook";//重定向allBook请求
    }

    //跳转到修改书籍页面
    @RequestMapping("/toUpdate")
    public String toUpdatePager(int id, Model model) {
        Books books = bookService.queryBookById(id);
        model.addAttribute("QBook", books);
        return "updateBook";
    }

    //修改书籍
    @RequestMapping("/updateBook")
    public String updateBook(Books books) {
        System.out.println("updatebook====" + books);
        int i = bookService.updateBook(books);
        if (i > 0) {
            System.out.println("添加成功" + books);
        }
//        List<Books> books1 = bookService.queryAllBook();
        return "redirect:/book/allBook";
    }

    //删除数据
    @RequestMapping("/deleteBook/{bookId}")
    public String deleteBook(@PathVariable("bookId") int id) {
        bookService.deleteBookById(id);
        return "redirect:/book/allBook";
    }

    //查询书籍
    @RequestMapping("/queryBook")
    public String queryBook(String queryBookName, Model model) {
        Books books = bookService.queryBookByName(queryBookName);
        List<Books> list=new ArrayList<Books>();
        list.add(books);

        if(books==null){
            list=bookService.queryAllBook();
            model.addAttribute("error","未查到");
        }

        model.addAttribute("list",list);
        return "allBook";


    }


}
