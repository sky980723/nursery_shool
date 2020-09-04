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
import java.util.Map;

/**
 * @author Administrator 这个里面放一些基本宣传的东西(前端首页介绍及相关维护接口、园区动态相关接口)
 */
@RestController
@RequestMapping("/api/index")
public class IndexController {

    @Autowired
    private IIndexService indexService;

    /**
     * 根据type查询不同的宣传(前端)
     *
     * @param type
     * @return
     */
    @GetMapping("/listPublicity")
    public Results<List<PublicityApp>> listPublicity(@RequestParam(name = "type") String type,
                                                     @RequestParam(name = "schoolId") String schoolId) {

        return indexService.listPublicity(type, schoolId);
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

        return indexService.login(account, password, request);
    }

    /**
     * 注册接口，注册成功后登陆
     * @param companySchool
     * @param request
     * @return
     */
    @PostMapping("/saveCompanySchool")
    public Results<String> saveCompanySchool(@RequestBody CompanySchool companySchool, HttpServletRequest request) {

        return indexService.saveCompanySchool(companySchool, request);
    }


    /**
     * 添加或修改老师
     *
     * @param teacher
     * @param request
     * @return
     */
    @PostMapping("/saveOrUpdateTeacher")
    public Results<String> saveOrUpdateTeacher(@RequestBody Teacher teacher, HttpServletRequest request) {

        return indexService.saveOrUpdateTeacher(teacher, request);
    }

    /**
     * 删除老师
     *
     * @param id
     * @return
     */
    @GetMapping("/deleteTeacher")
    public Results<String> deleteTeacher(@RequestParam(name = "id", required = true) String id) {

        return indexService.deleteTeacher(id);
    }

    /**
     * 获取老师集合，同时获取师资介绍的富文本(前端接口)
     *
     * @param schoolId    幼儿园的标识id
     * @param teacherName 老师姓名 支持模糊查询
     * @param page        分页参数 非必传
     * @return
     */
    @GetMapping("/listTeacher")
    public Results<Map<String, Object>> listTeacher(@RequestParam(name = "schoolId", required = false) String schoolId,
                                                    @RequestParam(name = "teacherName", required = false) String teacherName,
                                                    @RequestParam(name = "page", required = false) Integer page) {

        return indexService.listTeacher(schoolId, teacherName, page);
    }


    //后台维护子账号的接口
    @PostMapping("/saveOrUpdateComapanySchool")
    public Results<String> saveOrUpdateComapanySchool(@RequestBody CompanySchool companySchool,HttpServletRequest request) {

        return indexService.saveOrUpdateComapanySchool(companySchool,request);
    }

    //后台删除子账号
    @GetMapping("/deleteCompanySchool")
    public Results<String> deleteCompanySchool(@RequestParam(name = "id")String id,HttpServletRequest request) {

        return indexService.deleteCompanySchool(id,request);
    }

    //后台获取所有账号的集合
    @GetMapping("/listCompanySchool")
    public Results<List<CompanySchool>> listCompanySchool(@RequestParam(name = "page")int page,
                                                          @RequestParam(name = "condition",required = false)String condition,
                                                          HttpServletRequest request) {

        return indexService.listCompanySchool(page,condition,request);

    }

}
