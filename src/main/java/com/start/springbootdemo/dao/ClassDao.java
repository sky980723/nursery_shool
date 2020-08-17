package com.start.springbootdemo.dao;

import com.start.springbootdemo.entity.Class;

public interface ClassDao {
    Integer saveClass(Class studentClass);

    Integer updateClass(Class studentClass);
}
