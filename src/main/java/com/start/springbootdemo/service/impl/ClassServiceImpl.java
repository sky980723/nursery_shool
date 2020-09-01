package com.start.springbootdemo.service.impl;

import com.start.springbootdemo.dao.ClassDao;
import com.start.springbootdemo.entity.Class;
import com.start.springbootdemo.entity.Grade;
import com.start.springbootdemo.service.IClassService;
import com.start.springbootdemo.util.KeyGen;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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
        //同年级下不允许存在重名班级
        int count = classDao.countClass(studentClass.getGradeId(), studentClass.getClassName(), schoolId, studentClass.getId());
        if (count != 0) {
            results.setStatus("1");
            results.setMessage("同年级下不允许存在重名班级，请修改后再次操作");

            return results;
        }
        //根据id是否存在判断是添加还是修改
        if (StringUtils.isEmpty(studentClass.getId())) {
            //为空，需要添加班级
            studentClass.setId(KeyGen.uuid());
            studentClass.setAddtime(new Date());
            classDao.saveClass(studentClass);
        } else {
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

    @Override
    public Results<String> saveOrUpdateGrade(Grade grade, HttpServletRequest request) {
        Results<String> results = new Results<>();
        String schoolId = String.valueOf(request.getSession().getAttribute("schoolId"));
        if (StringUtils.isEmpty(schoolId)) {
            results.setStatus("1");
            results.setMessage("登录超时，请重新登录");

            return results;
        }
        //不管是添加还是修改，控制不要有重名的(同幼儿园内不要有同名年级)
        Integer count = classDao.countByGradeName(grade.getGradeName(), grade.getId(), schoolId);
        if (count > 0) {
            results.setStatus("1");
            results.setMessage("年级名不允许重复，请修改~");

            return results;
        }
        if (StringUtils.isEmpty(grade.getId())) {
            //id为空，是添加
            grade.setId(KeyGen.uuid());
            grade.setSchoolId(schoolId);
            //赋值orders
            grade.setOrders(classDao.getOrders(schoolId) + 1);
            grade.setAddtime(new Date());
            classDao.saveGrade(grade);
        } else {
            //id不为空，是修改
            grade.setUpdatetime(new Date());
            classDao.updateGrade(grade);
        }
        results.setStatus("0");

        return results;
    }

    @Transactional
    @Override
    public Results<String> deleteGrade(String id) {
        Results<String> results = new Results<>();
        //根据id删除一个年级
        classDao.deleteGrade(id);
        results.setStatus("0");

        return results;
    }

    @Override
    public Results<List<Grade>> listGrade(String schoolId) {
        Results<List<Grade>> results = new Results<>();
        //查询年级集合，同时查询班级集合
        List<Grade> list = classDao.listGrade(schoolId);
        for (Grade grade : list) {
            grade.setClassList(classDao.listClass(grade.getId()));
        }
        results.setStatus("0");
        results.setData(list);

        return results;
    }

}
