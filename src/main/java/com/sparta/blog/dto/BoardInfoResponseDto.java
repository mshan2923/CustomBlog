package com.sparta.blog.dto;

import com.sparta.blog.entity.Board;
import com.sparta.blog.entity.UserData;
import com.sparta.blog.repository.UserDataRepository;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Getter
public class BoardInfoResponseDto {
    private String author;
    private String title;
    private String contents;
    private Long boardId;
    private Long userId;
    private String username;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardInfoResponseDto(Board board) {
        this.author = board.getAuthor();
        this.title = board.getTitle();
        this.boardId = board.getBoardId();
        this.userId = board.getUserId();
        //this.username = userData.getUsername();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

    public BoardInfoResponseDto tryUserName(UserDataRepository userDataRepository)
    {
        this.username = userDataRepository.findUserDataByUserid(userId)
                .orElseThrow(() -> new NoSuchElementException("찾을 수 없음")).getUsername();

        return this;
    }
}
