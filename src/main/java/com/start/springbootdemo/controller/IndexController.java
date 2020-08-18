package com.start.springbootdemo.controller;

import com.start.springbootdemo.entity.CompanyUser;
import com.start.springbootdemo.entity.PublicityApp;
import com.start.springbootdemo.service.IIndexService;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/api/index")
public class IndexController {

    @Autowired
    private IIndexService indexService;

    /**
     * 根据type查询不同的宣传
     *
     * @param type
     * @return
     */
    @GetMapping("/listPublicity")
    public Results<List<PublicityApp>> listPublicity(@RequestParam(name = "type") String type) {

        return indexService.listPublicity(type);
    }

    /**
     * 前端页面的登录
     *
     * @param account  账号
     * @param password 密码
     * @return
     */
    @GetMapping("/login")
    public Results<CompanyUser> login(@RequestParam(name = "account") String account,
                                      @RequestParam(name = "password") String password) {

        return indexService.login(account, password);
    }
}
