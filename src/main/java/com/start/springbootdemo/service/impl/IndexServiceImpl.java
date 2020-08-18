package com.start.springbootdemo.service.impl;

import com.start.springbootdemo.dao.IndexDao;
import com.start.springbootdemo.entity.CompanySchool;
import com.start.springbootdemo.entity.PublicityApp;
import com.start.springbootdemo.service.IIndexService;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class IndexServiceImpl implements IIndexService {

    @Autowired
    private IndexDao indexDao;

    @Override
    public Results<List<PublicityApp>> listPublicity(String type) {
        Results<List<PublicityApp>> results = new Results<>();
        List<PublicityApp> list = indexDao.listPublicity(type);
        results.setStatus("0");
        results.setData(list);

        return results;
    }

    @Override
    public Results<CompanySchool> login(String account, String password, HttpServletRequest request) {
        Results<CompanySchool> results = new Results<>();
        //根据账号查询，账号字段在表中加了唯一索引
        CompanySchool companyUser = indexDao.getCompanySchool(account);
        if (companyUser == null) {
            results.setStatus("1");
            results.setMessage("无账号信息");

            return results;
        }else {
            if (!password.equals(companyUser.getPassword())) {
                results.setStatus("1");
                results.setMessage("密码错误，请检查");

                return results;
            }
        }
        results.setStatus("0");
        results.setData(companyUser);
        //sesstion中放参数
        request.getSession().setAttribute("schoolId",companyUser.getId());
        request.getSession().setAttribute("isDean",companyUser.getIsDean());

        return results;
    }




}
