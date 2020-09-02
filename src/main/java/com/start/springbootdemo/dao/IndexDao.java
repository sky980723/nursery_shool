package com.start.springbootdemo.dao;

import com.start.springbootdemo.entity.CompanySchool;
import com.start.springbootdemo.entity.PublicityApp;
import com.start.springbootdemo.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sky
 */
@Repository
public interface IndexDao {


    List<PublicityApp> listPublicity(@Param("type") String type, @Param("schoolId") String schoolId);

    CompanySchool getCompanySchool(@Param("account") String account);

    Integer saveTeacher(Teacher teacher);

    Integer updateTeacher(Teacher teacher);

    Integer deleteTeacher(@Param("id") String id);

    List<Teacher> listTeacher(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize,
                              @Param("teacherName") String teacherName, @Param("schoolId") String schoolId);

    Integer countTeacher(@Param("teacherName") String teacherName, @Param("schoolId") String schoolId);

    String getIntroduce(@Param("schoolId") String schoolId, @Param("type") int type);

    int countCompanySchool(@Param("schoolId") String schoolId, @Param("isDean") Integer isDean, @Param("account") String account,
                           @Param("id") String id, @Param("condition") String condition);

    Integer saveCompanySchool(CompanySchool companySchool);

    Integer updateCompanySchool(CompanySchool companySchool);

    Integer deleteCompanySchool(@Param("id") String id);

    List<CompanySchool> listCompanySchool(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize,
                                          @Param("condition") String condition,
                                          @Param("schoolId") String schoolId);
}
