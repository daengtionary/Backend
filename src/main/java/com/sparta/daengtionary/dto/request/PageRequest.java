package com.sparta.daengtionary.dto.request;


import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageRequest {
    private int page = 1;

    private int size = 10;

    private Sort.Direction direction = Sort.Direction.DESC;

    public org.springframework.data.domain.PageRequest of(int page,int size){
        this.page = page;
        this.size = size;
        return org.springframework.data.domain.PageRequest.of(page - 1,size);
    }
}
