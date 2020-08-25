package com.start.springbootdemo.dao;

import com.start.springbootdemo.entity.Patriarch;
import com.start.springbootdemo.entity.PatriarchStudent;
import com.start.springbootdemo.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentDao {
    List<Student> listStudent(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize,
                              @Param("classId") String classId, @Param("schoolId") String schoolId, @Param("name") String name);

    Patriarch getPatriarch(@Param("mobile") String mobile, @Param("schoolId") String schoolId);

    Integer savePatriarch(Patriarch patriarch);

    Integer updatePatriarch(Patriarch patriarch);

    Integer saveStudent(Student student);

    Integer savePatriarchStudent(PatriarchStudent patriarchStudent);
}
