package com.start.springbootdemo.entity;

import java.util.Date;

/**
 * @author Sky
 */
public class Banner {

    public String id;

    //图片地址
    private String imgUrl;

    //图片介绍(文字)
    private String introduce;

    //banner的类型
    private Integer types;

    //所属园区的id
    private String schoolId;

    private Date addtime;

    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Banner(String imgUrl, String schoolId) {
        this.imgUrl = imgUrl;
        this.schoolId = schoolId;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Integer getTypes() {
        return types;
    }

    public void setTypes(Integer types) {
        this.types = types;
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
