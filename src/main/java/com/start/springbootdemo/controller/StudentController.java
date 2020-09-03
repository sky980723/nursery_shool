package com.start.springbootdemo.controller;

import com.start.springbootdemo.entity.Patriarch;
import com.start.springbootdemo.entity.Student;
import com.start.springbootdemo.entity.StudentApply;
import com.start.springbootdemo.entity.StudentImg;
import com.start.springbootdemo.service.IStudentService;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

/**
 * @author Sky
 */
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
                                              @RequestParam(name = "openId", required = false) String openId) {

        return studentService.listStudent(page, name, classId, schoolId, openId);
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




    /**
     * 填充纯表单的操作(报名接口)(添加或修改)
     *
     * @param studentApply
     * @return
     */
    @PostMapping("/saveOrUpdateStudentApply")
    public Results<String> saveOrUpdateStudentApply(@RequestBody StudentApply studentApply) {

        return studentService.saveOrUpdateStudentApply(studentApply);
    }

    /**
     * 点赞、取消点赞的接口
     *
     * @param studentId
     * @param openId
     * @return
     */
    @GetMapping("/saveOrUpdateLike")
    public Results<String> saveOrUpdateLike(@RequestParam(name = "studentId") String studentId,
                                            @RequestParam(name = "openId") String openId) {

        return studentService.saveOrUpdateLike(studentId, openId);
    }

    /**
     * 获取openID
     *
     * @param code
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/getOpenId")
    public Results<String> getOpenId(@RequestParam(name = "code") String code, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {

        return studentService.getOpenId(code, request, response);
    }

    //获取code(待测试)
    @GetMapping("getCode")
    public Results<String> getCode(HttpServletResponse response) throws IOException {

        return studentService.getCode(response);
    }

    /**
     * 获取孩子的名片
     *
     * @param openId
     * @return
     */
    @GetMapping("/getStudent")
    public Results<Map<String, Object>> getStudent(@RequestParam(name = "openId") String openId) {

        return studentService.getStudent(openId);
    }

    //上传或修改孩子的相册
    @PostMapping("saveOrUpdateStudentImg")
    public Results<String> saveOrUpdateStudentImg(@RequestBody StudentImg studentImg) {

        return studentService.saveOrUpdateStudentImg(studentImg);
    }

    //删除相册
    @GetMapping("deleteStudentImg")
    public Results<String> deleteStudentImg(@RequestParam(name = "id")String id) {

        return studentService.deleteStudentImg(id);
    }

    //添加或者修改孩子信息
    @PostMapping("saveOrUpdateStudent")
    public Results<String> saveOrUpdateStudent(@RequestBody Student student) {

        return studentService.saveOrUpdateStudent(student);
    }

    //后台批量上传孩子
    @PostMapping(value = "/improtExcel",consumes = "multipart/form-data")
    public Results<String> improtExcel(@RequestParam(name = "file")MultipartFile file,
                                       HttpServletRequest request) {

        return studentService.improtExcel(file,request);
    }



}
