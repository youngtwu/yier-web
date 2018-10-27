package com.yier.platform.service;


import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yier.platform.conf.ApplicationConfig;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.HashSet;

/**
 * 文件上传工具
 *
 * @author 应卓
 */
@Component
public class Uploader {

    @Autowired
    private FastFileStorageClient client;

    @Autowired
    private ApplicationConfig applicationConfig;

    public String upload(InputStream inputStream, long size, String ext) {
        try {
            StorePath storePath = client.uploadFile(inputStream, size, ext, new HashSet<>());
            return applicationConfig.getUploadImageUrlPrefix() + storePath.getFullPath();// "http://192.168.0.215:7070/"+ storePath.getFullPath();//applicationConfig.getFastdfs().getUrlPrefix() + storePath.getFullPath();//
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    // 忽略异常
                }
            }
        }
    }

    public String upload(MultipartFile file) {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        if (ext.startsWith(".")) {
            ext = ext.substring(1);
        }

        try {
            return upload(file.getInputStream(), file.getSize(), ext);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void deleteFile(String urlPath){
        this.client.deleteFile(urlPath);
    }

}
