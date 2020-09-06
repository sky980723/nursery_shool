package com.start.springbootdemo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author Sky
 * 账号实体类，该表中账号可以登录后台、可以在前端发布园区动态
 */
public class CompanySchool {

    private String id;

    private String account;

    private String password;

    //是否是院长，好像这个字段是没啥用的  1：是园长;0:不是园长
    private Integer isDean;

    private String nickName;//昵称

    private String headImg;//头像

    private String schoolId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addtime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsDean() {
        return isDean;
    }

    public void setIsDean(Integer isDean) {
        this.isDean = isDean;
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
