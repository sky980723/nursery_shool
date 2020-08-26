package com.start.springbootdemo.service;

import com.start.springbootdemo.entity.CompanySchool;
import com.start.springbootdemo.entity.PublicityApp;
import com.start.springbootdemo.entity.Teacher;
import com.start.springbootdemo.util.Results;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Sky
 */
public interface IIndexService {
    Results<List<PublicityApp>> listPublicity(String type, String schoolId);

    Results<CompanySchool> login(String account, String password, HttpServletRequest request);

    Results<String> saveOrUpdateTeacher(Teacher teacher, HttpServletRequest request);

    Results<String> deleteTeacher(String id);

    Results<Map<String,Object>>listTeacher(String schoolId, String teacherName, Integer page);
}
