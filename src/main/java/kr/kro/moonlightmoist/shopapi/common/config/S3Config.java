package kr.kro.moonlightmoist.shopapi.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class S3Config {

    @Value("${bucket-name:default-name}")
    private String bucketName;

    @Value("${access-key-id:default-key-id}")
    private String accessKeyId;

    @Value("${secret-access-key:default-access-key}")
    private String secretAccessKey;

    public String getBucketName() { return bucketName; }
    public String getAccessKeyId() { return accessKeyId; }
    public String getSecretAccessKey() { return secretAccessKey; }
}
