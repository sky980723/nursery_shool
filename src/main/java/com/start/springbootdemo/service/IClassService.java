package com.start.springbootdemo.service;

import com.start.springbootdemo.entity.Class;
import com.start.springbootdemo.entity.Grade;
import com.start.springbootdemo.util.Results;

import javax.servlet.http.HttpServletRequest;

public interface IClassService {
    Results<String> saveOrUpdateClass(Class studentClass, HttpServletRequest request);

    Results<String> deleteClass(String id);

    Results<String> saveOrUpdateGrade(Grade grade);
}
