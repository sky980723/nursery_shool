package com.start.springbootdemo.service;

/**
 * @author Sky
 */
public interface IUploadService {
    String storeUrl(String fname, byte[] contents);
}
