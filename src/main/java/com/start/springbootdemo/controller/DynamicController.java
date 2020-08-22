package com.start.springbootdemo.controller;

import com.start.springbootdemo.entity.Dynamic;
import com.start.springbootdemo.service.IDynamicService;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/api/dynamic")
public class DynamicController {

    @Autowired
    private IDynamicService dynamicService;

    /**
     * 添加或修改动态(修改动态时处理图片需要详细处理一下)(添加、修改动态都是在前端进行的)
     * @param dynamic
     * @return
     */
    @PostMapping("/saveOrUpdateDynamic")
    public Results<String> saveOrUpdateDynamic(@RequestBody Dynamic dynamic) {

        return dynamicService.saveOrUpdateDynamic(dynamic);
    }
    //删除动态
    @GetMapping("/deleteDynamic")
    public Results<String> deleteDynamic(@RequestParam(name = "id",required = true)String id) {

        return dynamicService.deleteDynamic(id);
    }
}
