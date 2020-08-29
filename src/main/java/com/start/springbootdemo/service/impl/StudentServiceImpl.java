package com.start.springbootdemo.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.start.springbootdemo.dao.StudentDao;
import com.start.springbootdemo.entity.*;
import com.start.springbootdemo.service.IStudentService;
import com.start.springbootdemo.util.KeyGen;
import com.start.springbootdemo.util.Patterns;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Sky
 */
@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public Results<List<Student>> listStudent(Integer page, String name, String classId, String schoolId, String openId) {
        Results<List<Student>> results = new Results<>();
        int pageSize = Patterns.pageSize;
        int pageNo = (page - 1) * pageSize;
        List<Student> list = studentDao.listStudent(pageNo, pageSize, classId, schoolId, name, openId);
        results.setStatus("0");
        results.setData(list);

        return results;
    }

    /**
     * 添加家长信息，与学生生成绑定关系
     *
     * @param patriarch
     * @return
     */
    @Transactional
    @Override
    public Results<String> savePatriarch(Patriarch patriarch) {
        Results<String> results = new Results<>();
        //先创建家长的基本信息
        //根据手机号和schoolId查询是否存在对象，以此判断是添加还是修改
        Patriarch oldPatriarch = studentDao.getPatriarch(patriarch.getMobile(), patriarch.getSchoolId());
        String patriarchId;
        Date date = new Date();
        if (oldPatriarch == null) {
            //先添加一个家长的基本信息
            patriarchId = KeyGen.uuid();
            patriarch.setId(patriarchId);
            patriarch.setAddtime(date);
            studentDao.savePatriarch(patriarch);
        } else {
            //更新一下家长的基本信息
            patriarchId = patriarch.getId();
            patriarch.setUpdatetime(date);
            studentDao.updatePatriarch(patriarch);
        }
        //如果有学生对象，先去添加学生对象
        String studentId = KeyGen.uuid();
        if (patriarch.getStudent() != null) {
            //添加一个学生
            Student student = patriarch.getStudent();
            student.setId(studentId);
            student.setSchoolId(patriarch.getSchoolId());
            student.setAddtime(date);
            studentDao.saveStudent(student);
        } else {
            studentId = patriarch.getStudentId();
        }
        //创建关系
        PatriarchStudent patriarchStudent = new PatriarchStudent();
        patriarchStudent.setId(KeyGen.uuid());
        patriarchStudent.setPatriarchId(patriarchId);
        patriarchStudent.setStudentId(studentId);
        patriarchStudent.setRelation(patriarch.getRelation());
        patriarchStudent.setAddtime(date);
        studentDao.savePatriarchStudent(patriarchStudent);

        results.setStatus("0");

        return results;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Results<String> saveOrUpdateStudentApply(StudentApply studentApply) {
        Results<String> results = new Results<>();
        Date date = new Date();
        //根据id是否存在判断是添加还是修改
        if (StringUtils.isEmpty(studentApply.getId())) {
            //是添加
            //根据手机号和孩子姓名查询记录，如果存在就不要再次录入了
            int count = studentDao.countStudent(studentApply.getMobile(), studentApply.getStudentName(),
                    studentApply.getSchoolId());
            if (count != 0) {
                results.setStatus("1");
                results.setMessage("该宝贝已报名过,请勿重复提交表单~");

                return results;
            }
            studentApply.setId(KeyGen.uuid());
            studentApply.setAddtime(date);
            studentDao.saveStudentApply(studentApply);
        } else {
            //是修改
            studentApply.setUpdatetime(date);
            studentDao.updateStuentApply(studentApply);
        }
        results.setStatus("0");

        return results;
    }

    @Override
    public Results<String> saveOrUpdateLike(String studentId, String openId) {
        Results<String> results = new Results<>();
        //根据studentid 和 openID查询点赞记录表中是否存在记录
        Date date = new Date();
        StudentLikeRecord
                studentLikeRecord = studentDao.saveOrUpdateLike(studentId, openId);
        if (studentLikeRecord == null) {
            StudentLikeRecord slr = new StudentLikeRecord();
            //准备点赞,生成一条点赞记录
            slr.setId(KeyGen.uuid());
            slr.setStudentId(studentId);
            slr.setOpenId(openId);
            slr.setIsshow(1);
            slr.setAddtime(date);
            studentDao.saveLikeReocrd(slr);
        } else {
            //修改isshow字段，取反
            if (studentLikeRecord.getIsshow() == 1) {
                studentLikeRecord.setIsshow(0);
            } else {
                studentLikeRecord.setIsshow(1);
            }
            studentLikeRecord.setUpdatetime(date);
            studentDao.updateLikeRecord(studentLikeRecord);
        }
        results.setStatus("0");

        return results;
    }

}
