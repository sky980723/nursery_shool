package com.start.springbootdemo.dao;

import com.start.springbootdemo.entity.CompanySchool;
import com.start.springbootdemo.entity.PublicityApp;
import com.start.springbootdemo.entity.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Sky
 */
public interface IndexDao {


    List<PublicityApp> listPublicity(@Param("type") String type);

    CompanySchool getCompanySchool(@Param("account") String account);

    Integer saveTeacher(Teacher teacher);

    Integer updateTeacher(Teacher teacher);
}
