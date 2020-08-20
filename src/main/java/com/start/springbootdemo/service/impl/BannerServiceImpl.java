package com.start.springbootdemo.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.start.springbootdemo.dao.BannerDao;
import com.start.springbootdemo.entity.Banner;
import com.start.springbootdemo.service.IBannerService;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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

    @Transactional
    @Override
    public Results<String> deleteBanner(String id) {
        Results<String> results = new Results<>();
        bannerDao.deleteBanner(id);
        results.setStatus("0");

        return results;
    }

    @Override
    public Results<String> saveOrUpdate(Banner banner, HttpServletRequest request) {
        Results<String> results = new Results<>();
        String schoolId = String.valueOf(request.getSession().getAttribute("schoolId"));
        if (StringUtils.isEmpty(schoolId)) {
            results.setStatus("1");
            results.setMessage("登录超时，请重新登录");

            return  results;
        }
        if (StringUtils.isEmpty(banner.getId())) {
            //id 为空是添加
        }else {
            //ID不为空是修改

        }
        return null;
    }

}
