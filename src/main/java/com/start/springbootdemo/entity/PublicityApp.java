package com.start.springbootdemo.entity;

import java.util.Date;

/**
 * @author Sky
 * 园区首页的实体类，包含介绍富文本、图片、视频相关字段
 */
public class PublicityApp {

    private String id;

    private Integer type;//类型 1：富文本介绍 2：图片介绍  3：视频介绍

    private String introduce;//富文本内容

    private String imgUrl;//图片地址

    private String imgDescribe;//图片的文字说明

    private String videoUrl;//视频地址

    private String videoDescribe;//视频的文字说明

    private String schoolId;

    private Date addtime;

    private Date updatetime;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgDescribe() {
        return imgDescribe;
    }

    public void setImgDescribe(String imgDescribe) {
        this.imgDescribe = imgDescribe;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoDescribe() {
        return videoDescribe;
    }

    public void setVideoDescribe(String videoDescribe) {
        this.videoDescribe = videoDescribe;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
