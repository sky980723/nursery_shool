package com.start.springbootdemo.service;

import com.start.springbootdemo.entity.CompanySchool;
import com.start.springbootdemo.entity.PublicityApp;
import com.start.springbootdemo.entity.Teacher;
import com.start.springbootdemo.util.Results;
import org.apache.poi.util.Internal;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Sky
 */
public interface IIndexService {
    Results<List<PublicityApp>> listPublicity(String type, String schoolId, HttpServletRequest request);

    Results<CompanySchool> login(String account, String password, HttpServletRequest request);

    Results<String> saveOrUpdateTeacher(Teacher teacher, HttpServletRequest request);

    Results<String> deleteTeacher(String id);

    Results<Map<String,Object>>listTeacher(String schoolId, String teacherName, Integer page, HttpServletRequest request);

    Results<String> saveCompanySchool(CompanySchool companySchool, HttpServletRequest request);

    Results<String> saveOrUpdateComapanySchool(CompanySchool companySchool, HttpServletRequest request);

    Results<String> deleteCompanySchool(String id, HttpServletRequest request);

    Results<List<CompanySchool>> listCompanySchool(int page, String condition, HttpServletRequest request);

    String verifyToken(HttpServletRequest request);

    Results<List<Teacher>> listTeacherAdmin(Integer page, String teacherName, HttpServletRequest request);
}
