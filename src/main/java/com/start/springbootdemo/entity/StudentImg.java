package com.start.springbootdemo.entity;

import java.util.Date;

/**
 * @author Sky
 */
public class StudentImg {

    private String id;

    private String stduentId;

    private String imgUrl;

    private String sendWord;

    private Date addtime;

    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStduentId() {
        return stduentId;
    }

    public void setStduentId(String stduentId) {
        this.stduentId = stduentId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSendWord() {
        return sendWord;
    }

    public void setSendWord(String sendWord) {
        this.sendWord = sendWord;
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
