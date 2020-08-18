package com.start.springbootdemo.service;

import com.start.springbootdemo.entity.CompanySchool;
import com.start.springbootdemo.entity.PublicityApp;
import com.start.springbootdemo.util.Results;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IIndexService {
     Results<List<PublicityApp>>  listPublicity(java.lang.String type);

     Results<CompanySchool> login(String account, String password, HttpServletRequest request);

}
