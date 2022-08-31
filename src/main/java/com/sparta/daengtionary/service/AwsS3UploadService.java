package com.sparta.daengtionary.service;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class AwsS3UploadService {
    private final AmazonS3 amazonS3;

    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;


    @PostConstruct
    public AmazonS3Client amazonS3Client(){
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey,secretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata,String fileName){
        amazonS3.putObject(new PutObjectRequest(bucket,fileName,inputStream,objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String getFileUrl(String fileName){
        return amazonS3.getUrl(bucket,fileName).toString();
    }

    public void deleteFile(String fileName){
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket,fileName);
        amazonS3.deleteObject(deleteObjectRequest);
    }
}
