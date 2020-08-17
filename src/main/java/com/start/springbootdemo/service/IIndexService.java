package com.start.springbootdemo.service;

import com.start.springbootdemo.entity.CompanyUser;
import com.start.springbootdemo.entity.PublicityApp;
import com.start.springbootdemo.util.Results;

import java.util.List;

public interface IIndexService {
     Results<List<PublicityApp>>  listPublicity(java.lang.String type);

     Results<CompanyUser> login(String account, String password);
}
