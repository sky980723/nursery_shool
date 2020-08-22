package com.start.springbootdemo.service;

import com.start.springbootdemo.entity.Dynamic;
import com.start.springbootdemo.util.Results;

import java.util.List;

public interface IDynamicService {
    Results<String> saveOrUpdateDynamic(Dynamic dynamic);

    Results<String> deleteDynamic(String id);

    Results<List<Dynamic>> listDynamic(Integer page, String schoolId, String type);
}
