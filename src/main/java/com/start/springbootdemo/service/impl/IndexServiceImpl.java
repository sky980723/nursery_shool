package com.start.springbootdemo.service.impl;

import com.start.springbootdemo.dao.IndexDao;
import com.start.springbootdemo.entity.CompanySchool;
import com.start.springbootdemo.entity.PublicityApp;
import com.start.springbootdemo.entity.Teacher;
import com.start.springbootdemo.service.IIndexService;
import com.start.springbootdemo.util.KeyGen;
import com.start.springbootdemo.util.Patterns;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sun.plugin2.message.SetAppletSizeMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service
public class IndexServiceImpl implements IIndexService {

    @Autowired
    private IndexDao indexDao;

    @Override
    public Results<List<PublicityApp>> listPublicity(String type, String schoolId) {
        Results<List<PublicityApp>> results = new Results<>();
        List<PublicityApp> list = indexDao.listPublicity(type, schoolId);
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
        } else {
            if (!password.equals(companyUser.getPassword())) {
                results.setStatus("1");
                results.setMessage("密码错误，请检查");

                return results;
            }
        }
        results.setStatus("0");
        results.setData(companyUser);
        //sesstion中放参数
        request.getSession().setAttribute("schoolId", companyUser.getSchoolId());
        request.getSession().setAttribute("isDean", companyUser.getIsDean());

        return results;
    }

    @Override
    public Results<String> saveOrUpdateTeacher(Teacher teacher, HttpServletRequest request) {
        Results<String> results = new Results<>();
        //获取schoolId字段
        String schoolId = String.valueOf(request.getSession().getAttribute("schoolId"));
        if (StringUtils.isEmpty(schoolId)) {
            results.setStatus("1");
            results.setMessage("登录超时，请重新登录");

            return results;
        }
        if (StringUtils.isEmpty(teacher.getId())) {
            //id不存在，是添加
            teacher.setId(KeyGen.uuid());
            teacher.setSchoolId(schoolId);
            teacher.setAddtime(new Date());
            indexDao.saveTeacher(teacher);
        } else {
            //id存在，是修改
            teacher.setUpdatetime(new Date());
            indexDao.updateTeacher(teacher);
        }
        results.setStatus("0");

        return results;
    }

    @Transactional
    @Override
    public Results<String> deleteTeacher(String id) {
        Results<String> results = new Results<>();
        indexDao.deleteTeacher(id);
        results.setStatus("0");

        return results;
    }

    @Override
    public Results<Map<String, Object>> listTeacher(String schoolId, String teacherName, Integer page) {
        Results<Map<String,Object>> results = new Results<>();
        Map<String,Object> map = new HashMap<>();
        //这样就保证schoolId字段一定有值
        Integer pageSize = 0;
        Integer pageNo = 0;
        if (page != null) {
            pageSize = Patterns.pageSize;
            pageNo = (page - 1) * pageSize;
        }
        //获取老师的集合
        List<Teacher> list = indexDao.listTeacher(pageNo, pageSize, teacherName, schoolId);
        //获取师资水平的富文本字段
        String introduce = indexDao.getIntroduce(schoolId,4);
        map.put("teacherList",list);
        map.put("introduce",introduce);
        results.setStatus("0");
        results.setData(map);

        return results;
    }

    @Override
    public Results<String> saveCompanySchool(CompanySchool companySchool,HttpServletRequest request) {
        Results<String> results = new Results<>();
        //注册嘛 isDean字段是1，根据schoolId和isdean == 1的查询count，防止重复
        int count = indexDao.countCompanySchool(companySchool.getSchoolId());
        if (count != 0) {
            results.setStatus("1");
            results.setMessage("幼儿园标识已存在，请修改~");

            return results;
        }
        //添加一个幼儿园的主账号
        companySchool.setId(KeyGen.uuid());
        companySchool.setIsDean(1);
        companySchool.setAddtime(new Date());
        indexDao.saveCompanySchool(companySchool);
        //注册成功后直接登录
        request.getSession().setAttribute("schoolId",companySchool.getSchoolId());
        request.getSession().setAttribute("isDean",1);
        results.setStatus("0");

        return results;
    }


}
