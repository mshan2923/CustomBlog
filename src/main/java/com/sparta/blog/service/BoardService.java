package com.sparta.blog.service;

import com.sparta.blog.dto.BoardDeleteRequestDto;
import com.sparta.blog.dto.BoardRequestDto;
import com.sparta.blog.dto.BoardResponseDto;
import com.sparta.blog.entity.Board;
import com.sparta.blog.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service//////
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    //    2. 전체 게시글 목록 조회 API
    //    - 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
    //    - 작성 날짜 기준 내림차순으로 정렬하기
    public List<BoardResponseDto> getBoard() {
        // DB 조회
        /*
        List<Board> boards = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDto> boardResponseDtos = new ArrayList<>();
        for (Board board : boards) {
            boardResponseDtos.add(new BoardResponseDto(board));
        }
        return boardResponseDtos;
        */

        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(BoardResponseDto::new).toList();
    }

    //    3. 게시글 작성 API
//    - 제목, 작성자명, 비밀번호, 작성 내용을 저장하고
//    - 저장된 게시글을 Client 로 반환하기
    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        /*
        // RequestDto -> Entity
        Board board = new Board(requestDto);
        // DB 저장
        Board saveBoard = boardRepository.save(board);
        // Entity -> ResponseDto
        BoardResponseDto boardResponseDto = new BoardResponseDto(saveBoard);
        return boardResponseDto;
        */

        return new BoardResponseDto(boardRepository.save(new Board(requestDto)));
    }

//    4. 선택한 게시글 조회 API
//    - 선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기
//            (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)

//    public List<BoardResponseDto> getBoardsByKeyword(String keyword) {
//
//        List<Board> boards = boardRepository.findAllByContentsContainsOrderByModifiedAtDesc(keyword);
//        List<BoardResponseDto> boardResponseDtos = new ArrayList<>();
//        for(Board board : boards){
//            boardResponseDtos.add(new BoardResponseDto(board));
//        }
//        return boardResponseDtos;
//    }

    public BoardResponseDto getBoardById(Long id) {
        Board board = boardRepository.findBoardByBoardId(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다"));
        return new BoardResponseDto(board);
    }

//    5. 선택한 게시글 수정 API
//    - 수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
//    - 제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기

    @Transactional
    public BoardResponseDto updateBoardByPassword(Long id, BoardRequestDto requestDto) {

        Board board = boardRepository.findBoardByBoardId(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다"));

        try {
            //if (!board.getPassword().equals(requestDto.getPassword())) {
            //    throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
            //} else
            {
                board.update(requestDto);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return new BoardResponseDto(board);
    }

    //    6. 선택한 게시글 삭제 API
    //    - 삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
    //    - 선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환하기
    @Transactional
    public boolean deleteBoardByPassword(Long id, BoardDeleteRequestDto requestDto) {

        Board board = boardRepository.findBoardByBoardId(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다"));
        try {
            //if (!board.getPassword().equals(requestDto.getPassword())) {
            //    throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
            //} else
            {
                boardRepository.delete(board);
            }
        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
            return false;
        }
        return true;
    }

}
