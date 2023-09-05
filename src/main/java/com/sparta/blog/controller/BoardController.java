package com.sparta.blog.controller;

import com.sparta.blog.dto.BoardDeleteRequestDto;
import com.sparta.blog.dto.BoardRequestDto;
import com.sparta.blog.dto.BoardResponseDto;
import com.sparta.blog.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardController {
    // BoardController > BoardService > BoardRepository
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 1.전체 게시글 조회
    @GetMapping("/posts")
    public List<BoardResponseDto> getBoard() {
        return boardService.getBoard();
    }

    // 2.게시글 작성
    // Json형식으로 요청
    @PostMapping("/post")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto) {
        System.out.println(requestDto.toString());
        return boardService.createBoard(requestDto);

        //=============== UserId 넣어 줘야 하는데 - 이건 토큰으로
    }

    // 3. 선택한 게시글 조회 API
//    @GetMapping("/board/contents")
//    public List<BoardResponseDto> getBoardsByKeyword(String keyword) {
//        return boardService.getBoardsByKeyword(keyword);
//    }

    // 3. 선택한 게시글 조회 API
    @GetMapping("/board/{id}")
    public BoardResponseDto getBoardById(@PathVariable Long id) {
        return boardService.getBoardById(id);
    }

    // 4. 선택한 게시글 수정(password 일치)

    @PutMapping("/board/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto){
        return boardService.updateBoardByPassword(id,boardRequestDto);
    }

    // 5. 선택한 게시글 삭제(password 일치)
    @DeleteMapping("/board/{id}")
    public boolean deleteBoard(@PathVariable Long id, @RequestBody BoardDeleteRequestDto boardDeleteRequestDto){
        return boardService.deleteBoardByPassword(id, boardDeleteRequestDto);
    }

}
