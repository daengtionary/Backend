package com.sparta.daengtionary.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class TradeResponseDto {
    private Long tradeNo;
    private MemberResponseDto memberResponseDto;
    private String title;
    private String content;
    private String status;
    private int price;
    private int view;
    private List<String> tradeImgUrl;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createAt;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime modifiedAt;

    @Builder
    public TradeResponseDto(Long tradeNo,MemberResponseDto memberResponseDto,String title,
                            String content,int price,String status,int view,List<String> tradeImgUrl,
                            LocalDateTime createAt,LocalDateTime modifiedAt){
        this.tradeNo = tradeNo;
        this.memberResponseDto = memberResponseDto;
        this.title = title;
        this.content = content;
        this.price = price;
        this.status = status;
        this.view = view;
        this.tradeImgUrl = tradeImgUrl;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }


}
