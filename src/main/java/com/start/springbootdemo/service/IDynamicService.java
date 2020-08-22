package com.start.springbootdemo.service;

import com.start.springbootdemo.entity.Dynamic;
import com.start.springbootdemo.util.Results;

public interface IDynamicService {
    Results<String> saveOrUpdateDynamic(Dynamic dynamic);
}
