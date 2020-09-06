package com.start.springbootdemo.service;

import com.start.springbootdemo.entity.Class;
import com.start.springbootdemo.entity.Grade;
import com.start.springbootdemo.util.Results;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IClassService {
    Results<String> saveOrUpdateClass(Class studentClass, HttpServletRequest request);

    Results<String> deleteClass(String id);

    Results<String> saveOrUpdateGrade(Grade grade, HttpServletRequest request);

    Results<String> deleteGrade(String id);

    Results<List<Grade>> listGrade(String schoolId, HttpServletRequest request);
}
