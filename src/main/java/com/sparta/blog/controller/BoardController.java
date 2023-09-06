package com.sparta.blog.controller;

import com.sparta.blog.Provider.JwtUtil;
import com.sparta.blog.dto.BoardDeleteRequestDto;
import com.sparta.blog.dto.BoardInfoResponseDto;
import com.sparta.blog.dto.BoardRequestDto;
import com.sparta.blog.dto.BoardResponseDto;
import com.sparta.blog.service.BoardService;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {
    // BoardController > BoardService > BoardRepository
    private final BoardService boardService;
    private final JwtUtil jwtUtil;

    // 1.전체 게시글 조회
    @GetMapping("/boards")
    public List<BoardInfoResponseDto> getBoard() {
        return boardService.getBoard();
    }//선택한 글 , 이거랑 유저 이름 리턴해야됨

    // 2.게시글 작성
    // Json형식으로 요청
    @PostMapping("/post")
    public ResponseEntity<String> createBoard(@RequestBody BoardRequestDto requestDto, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String userName) {
        try {
            boardService.createBoard(requestDto, userName);
            return ResponseEntity.ok("Posted");
        }catch (NoSuchFieldException | IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 3. 선택한 게시글 조회 API
//    @GetMapping("/board/contents")
//    public List<BoardResponseDto> getBoardsByKeyword(String keyword) {
//        return boardService.getBoardsByKeyword(keyword);
//    }

    // 3. 선택한 게시글 조회 API
    @GetMapping("/board/{id}")
    public ResponseEntity<BoardInfoResponseDto> getBoardById(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(boardService.getBoardInfoById(id));
        }catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. 선택한 게시글 수정(password 일치)

    @PutMapping("/board/{id}")
    public ResponseEntity<String> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String userName){
        //return boardService.updateBoardByPassword(id,boardRequestDto);

        try {
            boardService.updateBoardByToken(id, boardRequestDto, userName);
            return ResponseEntity.ok("수정 완료");
        }catch (Exception e)
        {
            return ResponseEntity.badRequest().body("-->" + e.getMessage());
        }

    }

    // 5. 선택한 게시글 삭제(password 일치)
    @DeleteMapping("/board/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id, @CookieValue(JwtUtil.AUTHORIZATION_HEADER) String userName){
        //@RequestBody BoardDeleteRequestDto boardDeleteRequestDto
        //return boardService.deleteBoardByPassword(id, boardDeleteRequestDto);

        try {
            if (boardService.deleteBoardByToken(id, userName))
                return ResponseEntity.ok("제거 완료");
            else
                return ResponseEntity.badRequest().body("잘못된 요청");
        }catch (Exception e)
        {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
