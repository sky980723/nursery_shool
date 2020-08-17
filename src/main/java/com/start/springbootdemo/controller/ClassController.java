package com.start.springbootdemo.controller;

import com.start.springbootdemo.entity.Class;
import com.start.springbootdemo.service.IClassService;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 班级相关接口
 */
@RestController
@RequestMapping(value = "/api/class")
public class ClassController {

    @Autowired
    private IClassService classService;

    @PostMapping("/saveOrUpdateClass")
    public Results<String> saveOrUpdateClass(@RequestBody Class studentClass) {

        return classService.saveOrUpdateClass(studentClass);
    }
}
