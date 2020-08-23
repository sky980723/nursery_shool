package com.start.springbootdemo.dao;

import com.start.springbootdemo.entity.Dynamic;
import com.start.springbootdemo.entity.DynamicImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DynamicDao {
    Integer saveDynamic(Dynamic dynamic);

    Integer saveDynamicImg(DynamicImg dynamicImg);

    Integer updateDynamic(Dynamic dynamic);

    Integer deleteDynamicImg(@Param("dynamicId") String dynamicId);

    Integer deleteDynamic(@Param("id") String id);

    List<Dynamic> listDynamic(@Param("pageNo") Integer pageNo,@Param("pageSize") Integer pageSize,
                              @Param("schoolId") String schoolId,
                              @Param("type") String type);

    List<String> listDynamicImg(@Param("dynamicId") String dynamicId);
}
