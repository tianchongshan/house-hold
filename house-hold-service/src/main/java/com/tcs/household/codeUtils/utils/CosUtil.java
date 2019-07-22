package com.tcs.household.codeUtils.utils;

import cn.hutool.core.util.StrUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.AnonymousCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;

import com.tcs.household.codeUtils.configuration.CosConfig;
import com.tcs.household.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * 腾讯云Cos Util
 */
@Slf4j
public class CosUtil {

    @Autowired
    private COSCredentials cosCredentials;

    @Autowired
    private ClientConfig clientConfig;

    @Autowired
    private CosConfig cosConfig;

    private static CosUtil instant() {
        return SpringUtil.getBean(CosUtil.class);
    }

    /**
     * 创建Client对象
     * @return
     */
    private static COSClient client() {
        return new COSClient(instant().cosCredentials, instant().clientConfig);
    }

    /**
     * 关闭客户端
     * @param client
     */
    private static void release(COSClient client) {
        client.shutdown();
    }

    /**
     * 上传文件(无限制)
     * @param fileName
     * @param dir
     * @return
     */
    public static String uploadFileForUrl(String fileName, byte[] content, String dir) {
        File file = new File(fileName);
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            stream.write(content);
            stream.close();
        } catch (IOException e) {
            return null;
        }
        String url = uploadFileForUrl(file, dir);
        file.delete();
        return url;
    }
    /**
     * 上传文件(无限制)
     * @param file
     * @param dir
     * @return
     */
    public static String uploadFileForUrl(File file, String dir) {
        String bucketName = instant().cosConfig.getBucketName() + "-" + instant().cosConfig.getAppId();
        // 要上传到 COS 上的路径
        String extFileName = file.getName().substring(file.getName().lastIndexOf(".") - 1);
        String key = dir.concat(UUID.randomUUID().toString().concat(extFileName));
        COSClient cosClient = null;
        PutObjectResult putObjectResult = null;
        try {
            cosClient = client();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
            putObjectResult = cosClient.putObject(putObjectRequest);
        } finally {
            if (cosClient != null) {
                release(cosClient);
            }
        }
        return getUrl(bucketName, key);
    }

    /**
     * 上传文件(无限制)
     * @param fileName
     * @param dir
     * @return
     */
    public static String uploadFile(String fileName, byte[] content,  String dir) {
        File file = new File(fileName);
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            stream.write(content);
            stream.close();
        } catch (IOException e) {
            return null;
        }
        return uploadFile(file, dir);
    }

    /**
     * 上传文件(无限制)
     * @param file
     * @param dir
     * @return
     */
    public static String uploadFile(File file, String dir) {
        String bucketName = instant().cosConfig.getBucketName() + "-" + instant().cosConfig.getAppId();
        // 要上传到 COS 上的路径
        String extFileName = file.getName().substring(file.getName().lastIndexOf(".") - 1);
        String key = dir.concat(UUID.randomUUID().toString().concat(extFileName));
        COSClient cosClient = null;
        PutObjectResult putObjectResult = null;
        try {
            cosClient = client();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
            putObjectResult = cosClient.putObject(putObjectRequest);
        } finally {
            if (cosClient != null) {
                release(cosClient);
            }
        }
        return key;
    }

    /**
     * 根据Key下载文件
     * @param fileName
     * @param key
     * @return
     */
    public static File downloadFile(String fileName, String key) {
        String bucketName = instant().cosConfig.getBucketName() + "-" + instant().cosConfig.getAppId();
        // 指定要下载到的本地路径
        File downFile = new File(fileName);
        COSClient cosClient = null;
        PutObjectResult putObjectResult = null;
        try {
            cosClient = client();
            // 指定要下载的文件所在的 bucket 和对象键
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
            ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
        } finally {
            if (cosClient != null) {
                release(cosClient);
            }
        }
        return downFile;
    }

    /**
     * 根据Key下载文件
     * @param key
     * @return
     */
    public static void deleteFile(String key) {
        String bucketName = instant().cosConfig.getBucketName() + "-" + instant().cosConfig.getAppId();
        COSClient cosClient = null;
        try {
            cosClient = client();
            cosClient.deleteObject(bucketName, key);
        } finally {
            if (cosClient != null) {
                release(cosClient);
            }
        }
    }

    /**
     * 根据Key生成链接
     * @param key
     * @param expiration
     * @return
     */
    public static String getUrl(String key, Date expiration) {
        String bucketName = instant().cosConfig.getBucketName() + "-" + instant().cosConfig.getAppId();
        // 1 初始化用户身份信息, 匿名身份不用传入ak sk
        COSCredentials cred = new AnonymousCOSCredentials();
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
        COSClient cosclient = null;
        URL url = null;
        try {
            cosclient = new COSClient(cred, clientConfig);
            GeneratePresignedUrlRequest req =
                    new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET);
            req.setExpiration(expiration);
            url = cosclient.generatePresignedUrl(req);
        } finally {
            if (cosclient != null) {
                cosclient.shutdown();
            }
        }
        if (url != null) {
            return url.toString();
        }
        return "";
}

    /**
     * 获取共有读写URL
     * @param bucketName
     * @param key
     * @return
     */
    private static String getUrl(String bucketName, String key) {
        // 1 初始化用户身份信息, 匿名身份不用传入ak sk
        COSCredentials cred = new AnonymousCOSCredentials();
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
        COSClient cosclient = null;
        URL url = null;
        try {
            cosclient = new COSClient(cred, clientConfig);
            GeneratePresignedUrlRequest req =
                    new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET);
            url = cosclient.generatePresignedUrl(req);
        } finally {
            if (cosclient != null) {
                cosclient.shutdown();
            }
        }
        if (url != null) {
            return url.toString();
        }
        return "";
    }


    /**
     * 上传文件
     * @param mediaUrl 媒体原始URL
     * @param cosDir 需要上传COS的路径
     * @param tempDir 临时存取路径
     * @param timeOut 超时时间 : 秒
     * @return key
     */
    public static String uploadFileByUrl(String mediaUrl, String cosDir, String tempDir, String fileName, Integer timeOut, String extName) {
        try {
            URL url = new URL(mediaUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (timeOut != null && timeOut > 0) {
                conn.setConnectTimeout(timeOut * 1000);
            }
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            if (!tempDir.endsWith("/")) {
                tempDir += "/";
            }
            File dir = new File(tempDir);
            if (!dir.exists()) {
                if (!dir.exists()) {
                    dir.mkdirs();
                }
            }
            String fileExt = "";
            if (StrUtil.isNotBlank(extName)) {
                fileExt = extName;
            } else {
                //根据内容类型获取扩展名
                fileExt = getFileEndWith(conn.getHeaderField("Content-Type"));
                if (StrUtil.isBlank(fileExt)) {
                    return null;
                }
            }
            String filePath = fileName + fileExt;
            File localFile = new File(dir, filePath);
            BufferedInputStream bis = null;
            FileOutputStream fos = null;
            try {
                bis = new BufferedInputStream(conn.getInputStream());
                fos = new FileOutputStream(localFile);
                byte[] buf = new byte[8096];
                int size = 0;
                while ((size = bis.read(buf)) != -1) {
                    fos.write(buf, 0, size);
                }
            } catch (Exception e) {
                log.error("流处理异常,error={}" + e);
                return null;
            } finally {
                if (fos != null) {
                    fos.close();
                }
                if (bis != null) {
                    bis.close();
                }
                conn.disconnect();
            }
            log.info("下载媒体文件成功，filePath={}", filePath);
            String key = uploadFile(localFile, cosDir);
            // 删除服务器上的临时文件
            localFile.delete();
            return key;
        } catch (Exception e) {
            log.error("下载媒体文件失败，error={}" + e);
            return null;
        }
    }

    /**
     * 上传文件
     * @param mediaUrl 媒体原始URL
     * @param cosDir 需要上传COS的路径
     * @param tempDir 临时存取路径
     * @return 共有读写URL
     */
    public static String uploadFileByUrlToCosUrl(String mediaUrl, String cosDir, String tempDir, Integer timeOut, String extName) {
        String fileName = UUID.randomUUID().toString();
        String key = uploadFileByUrl(mediaUrl, cosDir, tempDir, fileName, timeOut, extName);
        String bucketName = instant().cosConfig.getBucketName() + "-" + instant().cosConfig.getAppId();
        return getUrl(bucketName, key);
    }

    /**
     * 上传文件
     * @param mediaUrl 媒体原始URL
     * @param cosDir 需要上传COS的路径
     * @param tempDir 临时存取路径
     * @return
     */
    public static String uploadFileByUrl(String mediaUrl, String cosDir, String tempDir, String fileName) {
        return uploadFileByUrl(mediaUrl, cosDir, tempDir, fileName, null, null);
    }

    /**
     * @param in          输入流
     * @param fileName    文件名带扩展名 xx.pdf
     * @param dir         存储目录
     * @param length      流长度
     * @param contentType 上传文件内容类型
     * @return
     */
    public static String uploadFileWithInputStream(InputStream in, String fileName, String dir, long length, String contentType) {
        String extFileName = fileName.substring(fileName.lastIndexOf(".") - 1);
        String key = dir.concat(UUID.randomUUID().toString().concat(extFileName));
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(length);
        objectMetadata.setContentType(contentType);
        COSClient cosclient = client();
        String bucketName = instant().cosConfig.getBucketName() + "-" + instant().cosConfig.getAppId();
        PutObjectResult putObjectResult = cosclient.putObject(bucketName, key, in, objectMetadata);
        String etag = putObjectResult.getETag();
        if (StrUtil.isBlank(etag)) {
            return null;
        }
        return key;
    }


    private static String getFileEndWith(String contentType) {
        String fileEndWith = "";
        if ("image/jpeg".equals(contentType)){
            fileEndWith = ".jpg";
        } else if ("audio/mpeg".equals(contentType)){
            fileEndWith = ".mp3";
        } else if ("audio/amr".equals(contentType)){
            fileEndWith = ".amr";
        } else if ("video/mp4".equals(contentType)){
            fileEndWith = ".mp4";
        } else if ("video/mpeg4".equals(contentType)){
            fileEndWith = ".mp4";
        } else if ("application/pdf".equals(contentType)) {
            fileEndWith = ".pdf";
        }
        return fileEndWith;
    }

}
