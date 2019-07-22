package com.tcs.household.codeUtils.configuration;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import com.tcs.household.codeUtils.anno.ConditionalOnClass;
import com.tcs.household.codeUtils.utils.CosUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConditionalOnClass(COSClient.class)
public class CosConfig {

    @Value("${cos.secret.id}")
    private String secretId;

    @Value("${cos.secret.key}")
    private String secretKey;

    @Value("${cos.env.path}")
    private String envPath;

    @Value("${cos.region}")
    private String region;

    @Value("${cos.bucket.name}")
    private String bucketName;

    @Value("${cos.app.id}")
    private String appId;

    /**
     * @return
     */
    @Bean
    public COSCredentials getCOSCredentials() {
        return new BasicCOSCredentials(secretId, secretKey);
    }

    /**
     * @return
     */
    @Bean
    public ClientConfig clientConfig() {
        return new ClientConfig(new Region(region));
    }

    @Bean
    public CosUtil CosUtil() {
        return new CosUtil();
    }

}
