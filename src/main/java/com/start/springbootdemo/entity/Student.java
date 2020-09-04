package com.start.springbootdemo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Sky
 */
public class Student {

    private String id;

    private String name;//学生姓名

    private String headImg;//头像

    private String sex;//性别

    private String classId;//所在班级id

    private String birthday;//生日 格式先定为yyyy-MM-dd

    private String interest;//兴趣

    private String strongPoint;//特长

    private String honor;//荣誉

    private String schoolId;//幼儿园的标识

    private Integer likeCount;//点赞数量

    private String manifesto;//宣言

    private String attendSchoolTime;//入学年份

    private String sendWord;//家长寄语

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addtime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    private Integer islike;//是否点过赞  1：点过   0：未点赞

    private String className;//班级名称

    private String gradeName;//年级名称

    private List<StudentImg> studentImgList = new ArrayList<>();

    public String getSendWord() {
        return sendWord;
    }

    public void setSendWord(String sendWord) {
        this.sendWord = sendWord;
    }

    public List<StudentImg> getStudentImgList() {
        return studentImgList;
    }

    public void setStudentImgList(List<StudentImg> studentImgList) {
        this.studentImgList = studentImgList;
    }

    public String getAttendSchoolTime() {
        return attendSchoolTime;
    }

    public void setAttendSchoolTime(String attendSchoolTime) {
        this.attendSchoolTime = attendSchoolTime;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getManifesto() {
        return manifesto;
    }

    public void setManifesto(String manifesto) {
        this.manifesto = manifesto;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getIslike() {
        return islike;
    }

    public void setIslike(Integer islike) {
        this.islike = islike;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getStrongPoint() {
        return strongPoint;
    }

    public void setStrongPoint(String strongPoint) {
        this.strongPoint = strongPoint;
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
