package com.start.springbootdemo.controller;

import com.start.springbootdemo.entity.Banner;
import com.start.springbootdemo.service.IBannerService;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/banner")
public class BannerController {
    @Autowired
    private IBannerService bannerService;

    @GetMapping(value = "/listBanner")
    public Results<List<Banner>> listBanner(@RequestParam(name = "schoolId")String schoolId,
                                            @RequestParam(name = "types",required = false)Integer types) {

        return bannerService.listBanner(schoolId,types);
    }
}
