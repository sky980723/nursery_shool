package com.start.springbootdemo.dao;

import com.start.springbootdemo.entity.CompanyUser;
import com.start.springbootdemo.entity.PublicityApp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Sky
 */
public interface IndexDao {


    List<PublicityApp> listPublicity(@Param("type") String type);

    CompanyUser getCompanyUser(@Param("account") String account);
}
