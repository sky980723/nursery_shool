package com.start.springbootdemo.dao;

import com.start.springbootdemo.entity.Class;
import com.start.springbootdemo.entity.Grade;
import org.apache.ibatis.annotations.Param;

/**
 * @author Administrator
 */
public interface ClassDao {
    Integer saveClass(Class studentClass);

    Integer updateClass(Class studentClass);

    Integer deleteClass(@Param("id") String id);

    Integer countByGradeName(@Param("gradeName") String gradeName,@Param("id") String id);

    Integer saveGrade(Grade grade);
}
