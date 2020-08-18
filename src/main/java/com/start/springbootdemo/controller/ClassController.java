package com.start.springbootdemo.controller;

import com.start.springbootdemo.entity.Class;
import com.start.springbootdemo.service.IClassService;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 班级相关接口
 */
@RestController
@RequestMapping(value = "/api/class")
public class ClassController {

    @Autowired
    private IClassService classService;

    /**
     * 后台添加或修改班级
     * @param studentClass
     * @return
     */
    @PostMapping("/saveOrUpdateClass")
    public Results<String> saveOrUpdateClass(@RequestBody Class studentClass, HttpServletRequest request) {

        return classService.saveOrUpdateClass(studentClass,request);
    }

    /**
     * 删除班级
     * @param id 班级的id
     * @return
     */
    @GetMapping("/deleteClass")
    public Results<String> deleteClass(@RequestParam(name = "id")String id) {

        return classService.deleteClass(id);
    }



}
