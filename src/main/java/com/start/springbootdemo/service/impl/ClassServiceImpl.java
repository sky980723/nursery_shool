package com.start.springbootdemo.service.impl;

import com.start.springbootdemo.dao.ClassDao;
import com.start.springbootdemo.entity.Class;
import com.start.springbootdemo.service.IClassService;
import com.start.springbootdemo.util.KeyGen;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class ClassServiceImpl implements IClassService {

    @Autowired
    private ClassDao classDao;


    @Override
    public Results<String> saveOrUpdateClass(Class studentClass, HttpServletRequest request) {
        Results<String> results = new Results<>();
        String schoolId = String.valueOf(request.getSession().getAttribute("schoolId"));
        if (StringUtils.isEmpty(schoolId)) {
            results.setStatus("1");
            results.setMessage("登录超时，请重新登录");

            return results;
        }
        studentClass.setSchoolId(schoolId);
        //根据id是否存在判断是添加还是修改
        if (StringUtils.isEmpty(studentClass.getId())) {
            //为空，需要添加班级
            studentClass.setId(KeyGen.uuid());
            studentClass.setAddtime(new Date());
            classDao.saveClass(studentClass);
        }else {
            //不为空，走修改
            studentClass.setUpdatetime(new Date());
            classDao.updateClass(studentClass);
        }
        results.setStatus("0");

        return results;
    }

    @Transactional
    @Override
    public Results<String> deleteClass(String id) {
        Results<String> results = new Results<>();
        classDao.deleteClass(id);
        //删除班级后，如果有学生与该班级存在绑定关系，应该同时删除绑定关系

        results.setStatus("0");

        return results;
    }
}
