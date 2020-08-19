package com.start.springbootdemo.dao;

import com.start.springbootdemo.entity.Banner;

import java.util.List;

public interface BannerDao {
    List<Banner> listBanner(String schoolId, Integer types);
}
