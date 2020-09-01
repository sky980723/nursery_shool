package com.start.springbootdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.start.springbootdemo.service.IUploadService;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private IUploadService uploadService;

    @RequestMapping(value = "/single", method = RequestMethod.POST, consumes = "multipart/form-data")
    public Results<String> uploadSingle(@RequestParam(value = "file", required = true) MultipartFile file)
            throws Exception {
        Results<String> result = new Results<String>();
        String url = process(file);
        result.setData(url);
        result.setStatus("0");
        return result;
    }

    @RequestMapping(value = "/wangEditor", method = RequestMethod.POST, consumes = "multipart/form-data")
    public JSONObject uploadTest(@RequestParam(value = "file", required = true) MultipartFile file,
                                 HttpServletRequest request) throws Exception {
        JSONObject obj = new JSONObject();
        String img = process(file);
        String[] imgs = { img };
        obj.put("errno", 0);
        obj.put("data", imgs);

        return obj;
    }

    private String process(MultipartFile file) throws Exception {
        byte[] contents = file.getBytes();
        String fname = file.getOriginalFilename();
        return uploadService.storeUrl(fname, contents);
    }

    @RequestMapping(value = "/multi", method = RequestMethod.POST, consumes = "multipart/form-data")
    public Results<List<String>> upload(@RequestParam(value = "file", required = true) MultipartFile[] file)
            throws Exception {
        Results<List<String>> result = new Results<>();
        List<String> items = new ArrayList<>();
        for (MultipartFile f : file) {
            String url = process(f);
            items.add(url);
        }
        result.setStatus("0");
        result.setData(items);
        return result;
    }
}
