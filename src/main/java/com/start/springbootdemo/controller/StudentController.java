package com.start.springbootdemo.controller;

import com.start.springbootdemo.entity.Patriarch;
import com.start.springbootdemo.entity.Student;
import com.start.springbootdemo.entity.StudentApply;
import com.start.springbootdemo.service.IStudentService;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NavigableMap;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    /**
     * 按条件获取学生集合，按添加时间倒序排序
     *
     * @param page
     * @param schoolId
     * @param name
     * @param classId
     * @return
     */
    @GetMapping("/listStudent")
    public Results<List<Student>> listStudent(@RequestParam(name = "page") Integer page,
                                              @RequestParam(name = "schoolId") String schoolId,
                                              @RequestParam(name = "name", required = false) String name,
                                              @RequestParam(name = "classId", required = false) String classId,
                                              @RequestParam(name = "openId",required = false)String openId) {

        return studentService.listStudent(page, name, classId, schoolId,openId);
    }

    /**
     * 家长与小孩生成绑定关系(先在家长表中添加或者更新家长信息，然后在生成与小孩的绑定关系)
     *
     * @param patriarch
     * @return
     */
    @PostMapping("/savePatriarch")
    public Results<String> savePatriarch(@RequestBody Patriarch patriarch) {
        //先验证一下信息是否完整

        return studentService.savePatriarch(patriarch);
    }


    //前端录入小孩，成功后返回小孩的id

    /**
     * 填充纯表单的操作(报名接口)(添加或修改)
     * @param studentApply
     * @return
     */
    @PostMapping("/saveOrUpdateStudentApply")
    public Results<String> saveOrUpdateStudentApply(@RequestBody StudentApply studentApply) {

        return  studentService.saveOrUpdateStudentApply(studentApply);
    }

    //点赞、取消点赞的接口
    @GetMapping("/saveOrUpdateLike")
    public Results<String> saveOrUpdateLike(@RequestParam(name = "studentId")String studentId,
                                            @RequestParam(name = "openId")String openId) {

        return studentService.saveOrUpdateLike(studentId,openId);
    }


}
