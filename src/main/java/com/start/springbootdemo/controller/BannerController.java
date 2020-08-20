package com.start.springbootdemo.controller;

import com.start.springbootdemo.entity.Banner;
import com.start.springbootdemo.service.IBannerService;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/banner")
public class BannerController {
    @Autowired
    private IBannerService bannerService;

    /**
     * 根据学校ID查询banner的集合，按添加顺序倒序展示(前端使用)
     *
     * @param schoolId 学校ID
     * @param types    banner的类型，目前只有一类，但是后续可能会增加其他类型
     * @return
     */
    @GetMapping(value = "/listBanner")
    public Results<List<Banner>> listBanner(@RequestParam(name = "schoolId") String schoolId,
                                            @RequestParam(name = "types", required = false) Integer types) {

        return bannerService.listBanner(schoolId, types);
    }

    /**
     * 根据ID删除一个banner
     * @param id
     * @return
     */
    @GetMapping("/deleteBanner")
    private Results<String> deleteBanner(@RequestParam(name = "id") String id) {

        return bannerService.deleteBanner(id);
    }

    //添加or修改一个banner
    @PostMapping("/saveOrUpdate")
    public  Results<String> saveOrUpdate(@RequestBody Banner banner, HttpServletRequest request) {

        return bannerService.saveOrUpdate(banner,request);
    }
}
