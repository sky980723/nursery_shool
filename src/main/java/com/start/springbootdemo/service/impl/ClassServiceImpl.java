package com.start.springbootdemo.service.impl;

import com.start.springbootdemo.dao.ClassDao;
import com.start.springbootdemo.entity.Class;
import com.start.springbootdemo.service.IClassService;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassServiceImpl implements IClassService {

    @Autowired
    private ClassDao classDao;


    @Override
    public Results<String> saveOrUpdateClass(Class studentClass) {
        Results<String> results = new Results<>();
        //根据id是否存在判断是添加还是修改
        return null;
    }
}
