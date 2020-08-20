package com.start.springbootdemo.dao;

import com.start.springbootdemo.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDao {
    List<Banner> listBanner(@Param("schoolId") String schoolId,@Param("types") Integer types);

    Integer deleteBanner(@Param("id") String id);

    Integer saveBanner(Banner banner);

    Integer updateBanner(Banner banner);

}
