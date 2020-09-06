package com.start.springbootdemo.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.start.springbootdemo.dao.IndexDao;
import com.start.springbootdemo.entity.CompanySchool;
import com.start.springbootdemo.entity.PublicityApp;
import com.start.springbootdemo.entity.Teacher;
import com.start.springbootdemo.service.IIndexService;
import com.start.springbootdemo.util.KeyGen;
import com.start.springbootdemo.util.Patterns;
import com.start.springbootdemo.util.Results;
import org.apache.poi.util.Internal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    public Results<List<PublicityApp>> listPublicity(String type, String schoolId, HttpServletRequest request) {
        Results<List<PublicityApp>> results = new Results<>();
        if (StringUtils.isEmpty(schoolId)) {
            schoolId = verifyToken(request);
        }
        if (StringUtils.isEmpty(schoolId)) {
            results.setStatus("1");
            results.setMessage("传参错误，请检查");

            return results;
        }
        List<PublicityApp> list = indexDao.listPublicity(type, schoolId);
        results.setStatus("0");
        results.setData(list);

        return results;
    }

    @Override
    public Results<CompanySchool> login(String account, String password, HttpServletRequest request) {
        Results<CompanySchool> results = new Results<>();
        String token = request.getHeader("token");
        CompanySchool companyUser = new CompanySchool();
        if (StringUtils.isNotEmpty(token)) {
            //根据token查询账号的
            companyUser = indexDao.getCompanySchoolById(token);
        } else {
            //根据账号查询，账号字段在表中加了唯一索引
            companyUser = indexDao.getCompanySchool(account);
        }
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
        //设置登录超时为2小时
        request.getSession().setMaxInactiveInterval(60 * 60 * 2);

        return results;
    }

    @Override
    public Results<String> saveOrUpdateTeacher(Teacher teacher, HttpServletRequest request) {
        Results<String> results = new Results<>();
        //获取schoolId字段
        String schoolId = verifyToken(request);
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
    public Results<Map<String, Object>> listTeacher(String schoolId, String teacherName, Integer page,
                                                    HttpServletRequest request) {
        Results<Map<String, Object>> results = new Results<>();
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(schoolId)) {
            schoolId = verifyToken(request);
        }
        if (StringUtils.isEmpty(schoolId)) {
            results.setStatus("1");
            results.setMessage("传参有误");

            return results;
        }
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
        String introduce = indexDao.getIntroduce(schoolId, 4);
        map.put("teacherList", list);
        map.put("introduce", introduce);
        results.setStatus("0");
        results.setData(map);

        return results;
    }

    @Override
    public Results<String> saveCompanySchool(CompanySchool companySchool, HttpServletRequest request) {
        Results<String> results = new Results<>();
        //注册嘛 isDean字段是1，根据schoolId和isdean == 1的查询count，防止重复
        int count = indexDao.countCompanySchool(companySchool.getSchoolId(), 1, null, companySchool.getAccount(), null);
        if (count != 0) {
            results.setStatus("1");
            results.setMessage("幼儿园标识已存在，请修改~");

            return results;
        }
        //添加之前先验证一下账号是否存在
        CompanySchool old = indexDao.getCompanySchool(companySchool.getAccount());
        if (old != null) {
            results.setStatus("1");
            results.setMessage("该账号已被使用，请更换后再次尝试~");

            return results;
        }
        //添加一个幼儿园的主账号
        companySchool.setId(KeyGen.uuid());
        companySchool.setIsDean(1);
        companySchool.setAddtime(new Date());
        indexDao.saveCompanySchool(companySchool);
        //设置登录2小时超时
        request.getSession().setMaxInactiveInterval(60 * 60 * 2);
        results.setStatus("0");

        return results;
    }

    @Override
    public Results<String> saveOrUpdateComapanySchool(CompanySchool companySchool, HttpServletRequest request) {
        Results<String> results = new Results<>();
        String schoolId = verifyToken(request);
        if (StringUtils.isEmpty(schoolId)) {
            results.setStatus("1");
            results.setMessage("登陆超时，请重新登录~");

            return results;
        }
        //根据schoolid和账号查询是否存在重复，账号不允许重复
        int count = indexDao.countCompanySchool(schoolId, null, companySchool.getAccount(), companySchool.getId(), null);
        if (count != 0) {
            results.setStatus("1");
            results.setMessage("账号重复，请修改后再次尝试~");

            return results;
        }
        Date date = new Date();
        //后台维护子账号的接口
        //根据id判断是添加还是修改
        companySchool.setIsDean(0);
        if (StringUtils.isEmpty(companySchool.getId())) {
            //添加
            companySchool.setId(KeyGen.uuid());
            companySchool.setSchoolId(schoolId);
            companySchool.setAddtime(date);
            indexDao.saveCompanySchool(companySchool);
        } else {
            //修改,无法修改isdean字段
            companySchool.setUpdatetime(date);
            indexDao.updateCompanySchool(companySchool);
        }
        results.setStatus("0");

        return results;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Results<String> deleteCompanySchool(String id, HttpServletRequest request) {
        Results<String> results = new Results<>();
        String schoolId = verifyToken(request);
        if (StringUtils.isEmpty(schoolId)) {
            results.setStatus("1");
            results.setMessage("登录超时，请重新登录~");

            return results;
        }
        //如果是主账号  禁止删除
        CompanySchool companySchool = indexDao.getCompanySchoolById(id);
        if (companySchool != null && companySchool.getIsDean() != null && companySchool.getIsDean() == 1) {
            results.setStatus("1");
            results.setMessage("主账号无法删除~");

            return results;
        }

        //根据id删除
        indexDao.deleteCompanySchool(id);
        results.setStatus("0");

        return results;
    }

    @Override
    public Results<List<CompanySchool>> listCompanySchool(int page, String condition, HttpServletRequest request) {
        Results<List<CompanySchool>> results = new Results<>();
        String schoolId = verifyToken(request);
        if (StringUtils.isEmpty(schoolId)) {
            results.setStatus("1");
            results.setMessage("登录超时，请重新登录~");

            return results;
        }
        int pageSize = 20;
        int pageNo = (page - 1) * pageSize;
        List<CompanySchool> list = indexDao.listCompanySchool(pageNo, pageSize, condition, schoolId);
        int count = indexDao.countCompanySchool(schoolId, null, null, null, condition);
        results.setStatus("0");
        results.setData(list);
        results.setCount(count);

        return results;
    }

    @Override
    public String verifyToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isNotEmpty(token)) {
            String schoolId = indexDao.getSchoolId(token);
            if (StringUtils.isNotEmpty(schoolId)) {
                return schoolId;
            }
        }
        return null;
    }

    @Override
    public Results<List<Teacher>> listTeacherAdmin(Integer page, String teacherName, HttpServletRequest request) {
        Results<List<Teacher>> results = new Results<>();
        String schoolId = verifyToken(request);
        if (StringUtils.isEmpty(schoolId)) {
            results.setStatus("1");
            results.setMessage("传参错误，请检查");

            return results;
        }
        int pageSize = 0;
        int pageNo = 0;
        if (page != null) {
            pageSize = Patterns.PAGE_SIZE_ADMIN;
            pageNo = (page - 1) * pageSize;
        }
        List<Teacher> list = indexDao.listTeacher(pageNo, pageSize, teacherName, schoolId);
        results.setStatus("0");
        results.setData(list);

        return results;
    }

}
