package com.sparta.daengtionary.aop.amazon;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AwsS3UploadService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<String> uploadListImg(List<MultipartFile> multipartFiles) {
        if (multipartFiles.isEmpty()) return null;

        List<String> imgUrlList = new ArrayList<>();

        for (MultipartFile file : multipartFiles) {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                imgUrlList.add(amazonS3.getUrl(bucket, fileName).toString());
            } catch (IOException e) {
                throw new CustomException(ErrorCode.IMAGE_UPLOAD_ERROR);
            }
        }
        return imgUrlList;
    }

    public String uploadImage(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) return null;

        String image = "";

        String fileName = createFileName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            image = amazonS3.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new CustomException(ErrorCode.IMAGE_UPLOAD_ERROR);
        }
        return image;
    }

    public void deleteFile(String fileName) {
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileName);
        amazonS3.deleteObject(request);
    }


    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {

        ArrayList<String> fileValidate = new ArrayList<>();
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        fileValidate.add(".png");
        fileValidate.add(".JPG");
        fileValidate.add(".JPEG");
        fileValidate.add(".PNG");
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        if (!fileValidate.contains(idxFileName)) {
            throw new CustomException(ErrorCode.WRONG_IMAGE_FORMAT);
        }
        return fileName.substring(fileName.lastIndexOf("."));

    }
}