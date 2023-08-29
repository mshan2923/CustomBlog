package com.example.memo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
public class MemoRequestDto {
    private  String username;
    private  String contents;
    private  String pw;

}
