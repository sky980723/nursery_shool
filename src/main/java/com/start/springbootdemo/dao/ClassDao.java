package com.start.springbootdemo.dao;

import com.start.springbootdemo.entity.Class;
import org.apache.ibatis.annotations.Param;

/**
 * @author Administrator
 */
public interface ClassDao {
    Integer saveClass(Class studentClass);

    Integer updateClass(Class studentClass);

    Integer deleteClass(@Param("id") String id);
}
