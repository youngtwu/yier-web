package com.yier.platform.service;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * 阿里云对象存储服务（Object Storage Service，简称OSS）为您提供基于网络的数据存取服务
 *
 */
@Service
public class AliyunOssService {
    private static final Logger logger = LoggerFactory.getLogger(AliyunOssService.class);

    // endpoint是访问OSS的域名。如果您已经在OSS的控制台上 创建了Bucket，请在控制台上查看域名。
    // 如果您还没有创建Bucket，endpoint选择请参看文档中心的“开发人员指南 > 基本概念 > 访问域名”，
    // 链接地址是：https://help.aliyun.com/document_detail/oss/user_guide/oss_concept/endpoint.html?spm=5176.docoss/user_guide/endpoint_region
    // endpoint的格式形如“http://oss-cn-hangzhou.aliyuncs.com/”，注意http://后不带bucket名称，
    // 比如“http://bucket-name.oss-cn-hangzhou.aliyuncs.com”，是错误的endpoint，请去掉其中的“bucket-name”。
    private static String endpoint = "https://oss-cn-beijing.aliyuncs.com";// "http://oss-cn-hangzhou.aliyuncs.com";

    // accessKeyId和accessKeySecret是OSS的访问密钥，您可以在控制台上创建和查看，
    // 创建和查看访问密钥的链接地址是：https://ak-console.aliyun.com/#/。
    // 注意：accessKeyId和accessKeySecret前后都没有空格，从控制台复制时请检查并去除多余的空格。
    private static String accessKeyId = "LTAIvYuVOwMPVYsL"; //"<yourAccessKeyId>";
    private static String accessKeySecret = "iardVx5SV3Bug9fUiQQiBh0e9TRi4e";// "<yourAccessKeySecret>";

    // Bucket用来管理所存储Object的存储空间，详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
    // Bucket命名规范如下：只能包括小写字母，数字和短横线（-），必须以小写字母或者数字开头，长度必须在3-63字节之间。
    private static String bucketName = "bucketvv";// "<yourBucketName>";

    // Object是OSS存储数据的基本单元，称为OSS的对象，也被称为OSS的文件。详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
    // Object命名规范如下：使用UTF-8编码，长度必须在1-1023字节之间，不能以“/”或者“\”字符开头。
    private static String firstKey = "my-first-key";

    public void uploadFile(String key,InputStream inputStream){
        DefaultCredentialProvider credsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration config = new ClientConfiguration();
        config.setMaxConnections(512); // 设置OSSClient使用的最大连接数，默认1024
        config.setSocketTimeout(60 * 1000); // 设置请求超时时间，默认50秒
        config.setMaxErrorRetry(3); // 设置失败请求重试次数，默认3次
        OSSClient ossClient =new OSSClient(endpoint, credsProvider, config);
        try {
            if (ossClient.doesBucketExist(bucketName)) {
                logger.info("您已经创建Bucket：" + bucketName + "。");
            } else {
                logger.info("您的Bucket不存在，创建Bucket：" + bucketName + "。");
                ossClient.createBucket(bucketName);
            }
            //阿里云目前有个问题 转换成 InputStream inputStrea 反而无法有效上传，待之后验证？？？？
            ossClient.putObject(bucketName, key, new File("E:\\projectOut\\banner\\lunch\\rongyunDefaulthead.png"));
//            ossClient.putObject(bucketName,key,inputStream);
            logger.info("Object：" + key + "存入OSS成功。");
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }

    public void uploadFile(String key,File file){
        DefaultCredentialProvider credsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration config = new ClientConfiguration();
        config.setMaxConnections(512); // 设置OSSClient使用的最大连接数，默认1024
        config.setSocketTimeout(60 * 1000); // 设置请求超时时间，默认50秒
        config.setMaxErrorRetry(3); // 设置失败请求重试次数，默认3次
        OSSClient ossClient =new OSSClient(endpoint, credsProvider, config);
        try {
            if (ossClient.doesBucketExist(bucketName)) {
                logger.info("您已经创建Bucket：" + bucketName + "。");
            } else {
                logger.info("您的Bucket不存在，创建Bucket：" + bucketName + "。");
                ossClient.createBucket(bucketName);
            }
            //阿里云目前有个问题 转换成 InputStream inputStrea 反而无法有效上传，待之后验证？？？？
            ossClient.putObject(bucketName, key, file);
//            ossClient.putObject(bucketName,key,inputStream);
            logger.info("Object：" + key + "存入OSS成功。");
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }
    public void deleteKey(String key){
        DefaultCredentialProvider credsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration config = new ClientConfiguration();
        config.setMaxConnections(512); // 设置OSSClient使用的最大连接数，默认1024
        config.setSocketTimeout(60 * 1000); // 设置请求超时时间，默认50秒
        config.setMaxErrorRetry(3); // 设置失败请求重试次数，默认3次
        OSSClient ossClient =new OSSClient(endpoint, credsProvider, config);
        try {
            ossClient.deleteObject(bucketName, key);
            logger.info("删除Object：" + key + "成功。");
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }
    public void viewBucket(){
        DefaultCredentialProvider credsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration config = new ClientConfiguration();
        config.setMaxConnections(512); // 设置OSSClient使用的最大连接数，默认1024
        config.setSocketTimeout(60 * 1000); // 设置请求超时时间，默认50秒
        config.setMaxErrorRetry(3); // 设置失败请求重试次数，默认3次
        OSSClient ossClient =new OSSClient(endpoint, credsProvider, config);
        try {
            ObjectListing objectListing = ossClient.listObjects(bucketName);
            List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
            logger.info("您有以下Object：");
            for (OSSObjectSummary object : objectSummary) {
                logger.info("\t" + object.getKey());
            }
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }



    public void loadInfo(){
        logger.info("Started");
        DefaultCredentialProvider credsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration config = new ClientConfiguration();
        config.setMaxConnections(512); // 设置OSSClient使用的最大连接数，默认1024
        config.setSocketTimeout(60 * 1000); // 设置请求超时时间，默认50秒
        config.setMaxErrorRetry(3); // 设置失败请求重试次数，默认3次
        OSSClient ossClient =new OSSClient(endpoint, credsProvider, config);
//        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {

            // 判断Bucket是否存在。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            if (ossClient.doesBucketExist(bucketName)) {
                System.out.println("您已经创建Bucket：" + bucketName + "。");
            } else {
                System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
                // 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
                // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
                ossClient.createBucket(bucketName);
            }

            // 查看Bucket信息。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            BucketInfo info = ossClient.getBucketInfo(bucketName);
            System.out.println("Bucket " + bucketName + "的信息如下：");
            System.out.println("\t数据中心：" + info.getBucket().getLocation());
            System.out.println("\t创建时间：" + info.getBucket().getCreationDate());
            System.out.println("\t用户标志：" + info.getBucket().getOwner());

            // 把字符串存入OSS，Object的名称为firstKey。详细请参看“SDK手册 > Java-SDK > 上传文件”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
            InputStream is = new ByteArrayInputStream("Hello OSS".getBytes());
            ossClient.putObject(bucketName, firstKey, is);
            System.out.println("Object：" + firstKey + "存入OSS成功。");

            // 下载文件。详细请参看“SDK手册 > Java-SDK > 下载文件”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/download_object.html?spm=5176.docoss/sdk/java-sdk/manage_object
            OSSObject ossObject = ossClient.getObject(bucketName, firstKey);
            InputStream inputStream = ossObject.getObjectContent();
            StringBuilder objectContent = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                objectContent.append(line);
            }
            inputStream.close();
            System.out.println("Object：" + firstKey + "的内容是：" + objectContent);

            // 文件存储入OSS，Object的名称为fileKey。详细请参看“SDK手册 > Java-SDK > 上传文件”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
            String fileKey = "p2.jpg";
            ossClient.putObject(bucketName, fileKey, new File("E:\\projectOut\\banner\\lunch\\p2.jpg"));
            System.out.println("Object：" + fileKey + "存入OSS成功。");

            // 查看Bucket中的Object。详细请参看“SDK手册 > Java-SDK > 管理文件”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
            ObjectListing objectListing = ossClient.listObjects(bucketName);
            List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
            System.out.println("您有以下Object：");
            for (OSSObjectSummary object : objectSummary) {
                System.out.println("\t" + object.getKey());
            }

//            // 删除Object。详细请参看“SDK手册 > Java-SDK > 管理文件”。
//            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
//            ossClient.deleteObject(bucketName, firstKey);
//            System.out.println("删除Object：" + firstKey + "成功。");
//            ossClient.deleteObject(bucketName, fileKey);
//            System.out.println("删除Object：" + fileKey + "成功。");

        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }

        logger.info("Completed");


    }


    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key,long second) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        // 设置URL过期时间为1小时。
        //Date expiration = new Date(new Date().getTime() + 3600 * 1000);
//        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        Date expiration = new Date(new Date().getTime() +  second * 1000);
        DefaultCredentialProvider credsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration config = new ClientConfiguration();
        config.setMaxConnections(512); // 设置OSSClient使用的最大连接数，默认1024
        config.setSocketTimeout(60 * 1000); // 设置请求超时时间，默认50秒
        config.setMaxErrorRetry(3); // 设置失败请求重试次数，默认3次
        OSSClient ossClient =new OSSClient(endpoint, credsProvider, config);
//        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        ossClient.shutdown();
        if (url != null) {
            return url.toString();
        }
        return null;
    }

/*    public static void main( String[] args )
    {
        String timestamp = "192272373";
        logger.info(" 目前判断：{} 是有效时间戳：{}",timestamp,Utils.isValidTimestamp(timestamp));
        timestamp = "1234567890";
        logger.info(" 目前判断：{} 是有效时间戳：{}",timestamp,Utils.isValidTimestamp(timestamp));
        timestamp = "1234567890123";
        logger.info(" 目前判断：{} 是有效时间戳：{}",timestamp,Utils.isValidTimestamp(timestamp));
        timestamp = "15355001";
        logger.info(" 目前判断：{} 是有效时间戳：{}",timestamp,Utils.isValidTimestamp(timestamp));
        timestamp = "a123456789";
        logger.info(" 目前判断：{} 是有效时间戳：{}",timestamp,Utils.isValidTimestamp(timestamp));
        System.out.println( "Hello World!" );
    }*/
}
