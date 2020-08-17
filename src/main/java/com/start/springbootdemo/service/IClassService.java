package com.start.springbootdemo.service;

import com.start.springbootdemo.entity.Class;
import com.start.springbootdemo.util.Results;

public interface IClassService {
    Results<String> saveOrUpdateClass(Class studentClass);
}
