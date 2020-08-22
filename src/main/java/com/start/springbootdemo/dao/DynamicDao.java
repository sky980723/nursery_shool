package com.start.springbootdemo.dao;

import com.start.springbootdemo.entity.Dynamic;
import com.start.springbootdemo.entity.DynamicImg;
import org.apache.ibatis.annotations.Param;

public interface DynamicDao {
    Integer saveDynamic(Dynamic dynamic);

    Integer saveDynamicImg(DynamicImg dynamicImg);

    Integer updateDynamic(Dynamic dynamic);

    Integer deleteDynamicImg(@Param("dynamicId") String dynamicId);

    Integer deleteDynamic(@Param("id") String id);
}
