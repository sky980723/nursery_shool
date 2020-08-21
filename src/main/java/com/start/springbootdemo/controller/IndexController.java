package com.start.springbootdemo.controller;

import com.start.springbootdemo.entity.CompanySchool;
import com.start.springbootdemo.entity.PublicityApp;
import com.start.springbootdemo.entity.Teacher;
import com.start.springbootdemo.service.IIndexService;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Administrator 这个里面放一些基本宣传的东西(前端首页介绍及相关维护接口、园区动态相关接口)
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
     * 前端页面的登录(前后端通用)
     *
     * @param account  账号
     * @param password 密码
     * @return
     */
    @GetMapping("/login")
    public Results<CompanySchool> login(@RequestParam(name = "account") String account,
                                        @RequestParam(name = "password") String password, HttpServletRequest request) {

        return indexService.login(account, password,request);
    }

    //添加老师
    @PostMapping("/saveOrUpdateTeacher")
    public Results<String> saveOrUpdateTeacher(@RequestBody Teacher teacher,HttpServletRequest request) {

        return indexService.saveOrUpdateTeacher(teacher,request);
    }

}
