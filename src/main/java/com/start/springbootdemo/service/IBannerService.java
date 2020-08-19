package com.start.springbootdemo.service;

import com.start.springbootdemo.entity.Banner;
import com.start.springbootdemo.util.Results;

import java.util.List;

public interface IBannerService {
    Results<List<Banner>> listBanner(String schoolId, Integer types);
}
