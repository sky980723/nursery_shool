package com.start.springbootdemo.service;

import com.start.springbootdemo.entity.Banner;
import com.start.springbootdemo.util.Results;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IBannerService {
    Results<List<Banner>> listBanner(String schoolId, Integer types);

    Results<String> deleteBanner(String id);

    Results<String> saveOrUpdate(Banner banner, HttpServletRequest request);
}
