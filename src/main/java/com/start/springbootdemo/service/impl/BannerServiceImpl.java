package com.start.springbootdemo.service.impl;

import com.start.springbootdemo.dao.BannerDao;
import com.start.springbootdemo.entity.Banner;
import com.start.springbootdemo.service.IBannerService;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl implements IBannerService {

    @Autowired
    private BannerDao bannerDao;

    @Override
    public Results<List<Banner>> listBanner(String schoolId, Integer types) {
        Results<List<Banner>> results = new Results<>();
        List<Banner> list = bannerDao.listBanner(schoolId,types);
        return null;
    }
}
