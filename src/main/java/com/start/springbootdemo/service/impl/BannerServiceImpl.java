package com.start.springbootdemo.service.impl;

import com.start.springbootdemo.dao.BannerDao;
import com.start.springbootdemo.entity.Banner;
import com.start.springbootdemo.service.IBannerService;
import com.start.springbootdemo.util.KeyGen;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author Sky
 */
@Service
public class BannerServiceImpl implements IBannerService {

    @Autowired
    private BannerDao bannerDao;

    @Override
    public Results<List<Banner>> listBanner(String schoolId, Integer types, HttpServletRequest request) {
        Results<List<Banner>> results = new Results<>();
        if (StringUtils.isEmpty(schoolId)) {
            schoolId = String.valueOf(request.getSession().getAttribute("schoolId"));
        }
        if (StringUtils.isEmpty(schoolId)) {
            results.setStatus("1");
            results.setMessage("登录超时，请重新登录");

            return  results;
        }
        List<Banner> list = bannerDao.listBanner(schoolId,types);
        results.setStatus("0");
        results.setData(list);

        return results;
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
            banner.setId(KeyGen.uuid());
            banner.setSchoolId(schoolId);
            banner.setAddtime(new Date());
            bannerDao.saveBanner(banner);
        }else {
            //ID不为空是修改
            banner.setUpdatetime(new Date());
            bannerDao.updateBanner(banner);
        }
        results.setStatus("0");

        return results;
    }

}
