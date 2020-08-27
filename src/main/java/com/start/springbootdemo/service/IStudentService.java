package com.start.springbootdemo.service;

import com.start.springbootdemo.entity.Patriarch;
import com.start.springbootdemo.entity.Student;
import com.start.springbootdemo.entity.StudentApply;
import com.start.springbootdemo.util.Results;

import java.util.List;

public interface IStudentService {
    Results<List<Student>> listStudent(Integer page, String name, String classId, String schoolId);

    Results<String> savePatriarch(Patriarch patriarch);

    Results<String> saveOrUpdateStudentApply(StudentApply studentApply);
}
