package com.start.springbootdemo.service.impl;

import com.obs.services.ObsClient;
import com.start.springbootdemo.service.IUploadService;
import com.start.springbootdemo.util.FileStore;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class UploadServiceImpl implements IUploadService {

    private static String accesskey = "WHW9UYCOIYEKFARNLHDO";
    private static String accesskeysecret = "1DsMXZ1lEyTdoRHgHKidSGc8rVRldY6VKdjI24cA";
    private static String endpoint = "obs.cn-north-4.myhuaweicloud.com";
    private static String bucketname = "shijizhongshi-image";
    private static String domain = "https://shijizhongshi-image.obs.cn-north-4.myhuaweicloud.com/";

    @Override
    public String storeUrl(String fname, byte[] contents) {
        String dpath = FileStore.buildpath();
        /// static/upload/2015/11/16/random_sequence_code/random_sequece.name
        String fpath = dpath + fname;

        // Create a new OSSClient instance with CNAME support
//		OSSClient client = new OSSClient(endpoint, accesskey, accesskeysecret,
//				new ClientConfiguration().setSupportCname(true));


        ObsClient obsClient = new ObsClient(accesskey,accesskeysecret,endpoint);

        // Do some operations with the instance...
        // 构造上传请求

        obsClient.putObject(bucketname,fpath,new ByteArrayInputStream(contents));

        //PutObjectRequest put = new PutObjectRequest(bucketname, fpath, new ByteArrayInputStream(contents));
        // 文件元信息的设置是可选的
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setContentType("application/octet-stream"); //
        // 设置content-type
        // metadata.setContentMD5(BinaryUtil.calculateBase64Md5(uploadFilePath));
        // // 校验MD5
        // put.setMetadata(metadata);

//		try {
//			obsClient.putObject(put);
//		} catch (Exception e) {
//			throw new FileStorageException(e);
//		}
//		// Shutdown the instance to release any allocated resources
//		obsClient.shutdown();

        try {
            obsClient.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return domain + fpath;
    }
}
