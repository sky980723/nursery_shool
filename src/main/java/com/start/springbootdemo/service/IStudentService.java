package com.start.springbootdemo.service;

import com.start.springbootdemo.entity.Patriarch;
import com.start.springbootdemo.entity.Student;
import com.start.springbootdemo.entity.StudentApply;
import com.start.springbootdemo.entity.StudentImg;
import com.start.springbootdemo.util.Results;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface IStudentService {
    Results<List<Student>> listStudent(Integer page, String name, String classId, String schoolId, String openId);

    Results<String> savePatriarch(Patriarch patriarch);

    Results<String> saveOrUpdateStudentApply(StudentApply studentApply);

    Results<String> saveOrUpdateLike(String studentId, String openId);

    Results<String> getOpenId(String code, HttpServletRequest request, HttpServletResponse response) throws IOException;

    Results<Map<String, Object>> getStudent(String openId);

    Results<String> getCode(HttpServletResponse response) throws IOException ;

    Results<String> saveOrUpdateStudentImg(StudentImg studentImg);

    Results<String> saveOrUpdateStudent(Student student);

    Results<String> deleteStudentImg(String id);
}
