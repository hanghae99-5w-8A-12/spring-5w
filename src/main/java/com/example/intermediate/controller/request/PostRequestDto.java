package com.example.intermediate.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String title; //게시판 제목
    private String content; //게시판 작성글
    private String imageUrl; //이미지 Url
}
