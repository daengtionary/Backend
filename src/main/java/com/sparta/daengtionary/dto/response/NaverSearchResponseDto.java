package com.sparta.daengtionary.dto.response;

import lombok.Data;

@Data
public class NaverSearchResponseDto {
    private int display;
    private Item[] items;

    @Data
    static class Item{
        public String title;
        public String link;
        public String description;
        public String telephone;
        public String address;
        public String roadAddress;
        public String mapx;
        public String mapy;
    }
}