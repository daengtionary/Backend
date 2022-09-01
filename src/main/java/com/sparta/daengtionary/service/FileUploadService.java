package com.sparta.daengtionary.service;


import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateTimeKeyDeserializer;
import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.MapImg;
import com.sparta.daengtionary.dto.response.MapImgResponseDto;
import com.sparta.daengtionary.dto.response.MapResponseDto;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.tika.Tika;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final AwsS3UploadService awsS3UploadService;

    public MapImgResponseDto uploadImage(MultipartFile file,String dirName)throws IOException{
        String fileName = dirName + "/" + createFileName(file.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        InputStream inputStream = file.getInputStream();

        awsS3UploadService.uploadFile(inputStream,objectMetadata,fileName);

        return MapImgResponseDto.builder()
                .imgName(fileName)
                .imgUrl(awsS3UploadService.getFileUrl(fileName))
                .build();
    }

    private String createFileName(String fileName) throws IOException {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName){
        ArrayList<String> fileValidate = new ArrayList<>();
        fileValidate.add(".jpg");
        fileValidate.add("jpeg");
        fileValidate.add(".png");
        fileValidate.add(".JPG");
        fileValidate.add(".JPEG");
        fileValidate.add(".PNG");
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        if(!fileValidate.contains(idxFileName)){
            throw new CustomException(ErrorCode.WRONG_IMAGE_FORMAT);
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }


    private String checkImageType(MultipartFile file)throws IOException {
        Tika tika = new Tika();
        String mimeType = tika.detect(file.getInputStream());

        if(!mimeType.startsWith("image/")){
            throw new CustomException(ErrorCode.WRONG_IMAGE_FORMAT);
        }

        return mimeType.substring(6);
    }

}
