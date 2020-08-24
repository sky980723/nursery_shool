package com.start.springbootdemo.controller;

import com.start.springbootdemo.service.IWeixinBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/binding")
public class WeixinBindController {

    @Autowired
    private IWeixinBindService weixinBindService;

}
