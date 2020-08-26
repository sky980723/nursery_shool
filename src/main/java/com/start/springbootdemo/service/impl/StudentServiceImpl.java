package com.start.springbootdemo.service.impl;

import com.start.springbootdemo.dao.StudentDao;
import com.start.springbootdemo.entity.Patriarch;
import com.start.springbootdemo.entity.PatriarchStudent;
import com.start.springbootdemo.entity.Student;
import com.start.springbootdemo.service.IStudentService;
import com.start.springbootdemo.util.KeyGen;
import com.start.springbootdemo.util.Patterns;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public Results<List<Student>> listStudent(Integer page, String name, String classId, String schoolId) {
        Results<List<Student>> results = new Results<>();
        Integer pageSize = Patterns.pageSize;
        Integer pageNo = (page - 1) * pageSize;
        List<Student> list = studentDao.listStudent(pageNo, pageSize, classId, schoolId, name);
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

}
