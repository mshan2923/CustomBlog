package com.example.memo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemoResponseDto {
    private  Long id;
    private  String username;
    private  String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MemoResponseDto(Memo data)
    {
        id = data.getId();
        username = data.getUsername();
        contents = data.getContents();

        createdAt = data.getCreatedAt();
        modifiedAt = data.getModifiedAt();
    }
}
