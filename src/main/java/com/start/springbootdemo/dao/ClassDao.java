package com.start.springbootdemo.dao;

import com.start.springbootdemo.entity.Class;

/**
 * @author Administrator
 */
public interface ClassDao {
    Integer saveClass(Class studentClass);

    Integer updateClass(Class studentClass);
}
