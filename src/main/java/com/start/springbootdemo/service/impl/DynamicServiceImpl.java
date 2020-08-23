package com.start.springbootdemo.service.impl;

import com.start.springbootdemo.dao.DynamicDao;
import com.start.springbootdemo.entity.Dynamic;
import com.start.springbootdemo.entity.DynamicImg;
import com.start.springbootdemo.service.IDynamicService;
import com.start.springbootdemo.util.KeyGen;
import com.start.springbootdemo.util.Patterns;
import com.start.springbootdemo.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class DynamicServiceImpl implements IDynamicService {

    @Autowired
    private DynamicDao dynamicDao;

    @Transactional
    @Override
    public Results<String> saveOrUpdateDynamic(Dynamic dynamic) {
        Results<String> results = new Results<>();
        if (StringUtils.isEmpty(dynamic.getId())) {
            //ID不存在 是添加
            //创建ID
            String id = KeyGen.uuid();
            dynamic.setId(id);
            dynamic.setAddtime(new Date());
            dynamicDao.saveDynamic(dynamic);
            //如果图片集合内存在图片，批量保存图片
            saveImgList(dynamic, id);
        } else {
            //存在，是修改
            dynamic.setUpdatetime(new Date());
            dynamicDao.updateDynamic(dynamic);
            //删除原有的动态内的图片
            dynamicDao.deleteDynamicImg(dynamic.getId());
            //保存本次修改时携带的图片集合
            saveImgList(dynamic, dynamic.getId());
        }
        results.setStatus("0");

        return results;
    }

    @Transactional
    @Override
    public Results<String> deleteDynamic(String id) {
        Results<String> results = new Results<>();
        //删除动态
        dynamicDao.deleteDynamic(id);
        //删除所属该动态的图片集合
        dynamicDao.deleteDynamicImg(id);
        //可以在这个地方加一个操作记录的保存，方便后期追溯

        results.setStatus("0");

        return results;
    }

    @Override
    public Results<List<Dynamic>> listDynamic(Integer page, String schoolId, String type) {
        Results<List<Dynamic>> results = new Results<>();
        Integer pageSize = Patterns.pageSize;
        Integer pageNo = (page -1)* pageSize;
        List<Dynamic> list = dynamicDao.listDynamic(pageNo,pageSize,schoolId,type);
        for (Dynamic dynamic : list) {
            //按顺序查询图片集合并赋值
            dynamic.setImgUrlList(dynamicDao.listDynamicImg(dynamic.getId()));
        }
        results.setStatus("0");
        results.setData(list);

        return results;
    }

    public void saveImgList(Dynamic dynamic, String id) {
        if (dynamic.getImgUrlList() != null && dynamic.getImgUrlList().size() > 0) {
            int orders = 1;
            for (String imgUrl : dynamic.getImgUrlList()) {
                DynamicImg dynamicImg = new DynamicImg();
                dynamicImg.setId(KeyGen.uuid());
                dynamicImg.setDynamicId(id);
                dynamicImg.setImgUrl(imgUrl);
                dynamicImg.setOrders(orders);
                dynamicImg.setAddtime(new Date());
                dynamicDao.saveDynamicImg(dynamicImg);
                orders++;
            }
        }
    }
}
