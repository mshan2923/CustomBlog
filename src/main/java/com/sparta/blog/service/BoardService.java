package com.sparta.blog.service;

import com.sparta.blog.Provider.JwtUtil;
import com.sparta.blog.Security.EncoderConfig;
import com.sparta.blog.dto.BoardDeleteRequestDto;
import com.sparta.blog.dto.BoardRequestDto;
import com.sparta.blog.dto.BoardResponseDto;
import com.sparta.blog.entity.Board;
import com.sparta.blog.entity.UserData;
import com.sparta.blog.repository.BoardRepository;
import com.sparta.blog.repository.UserDataRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service//////
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserDataRepository userDataRepository;
    private final JwtUtil jwtUtil;



    //    2. 전체 게시글 목록 조회 API
    public List<BoardResponseDto> getBoard() {
        // DB 조회
        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(BoardResponseDto::new).toList();
    }

    //    3. 게시글 작성 API
    public BoardResponseDto createBoard(BoardRequestDto requestDto, String token) throws NoSuchFieldException {
        if (!jwtUtil.validateToken(jwtUtil.substringToken(token)))
            throw new IllegalArgumentException("Token Error");

        Optional<UserData> member = userDataRepository.findByUsername(jwtUtil.getUserInfoFromToken(jwtUtil.substringToken(token)).getSubject());
        if (member.isEmpty())
            throw new NoSuchFieldException("해당 유저가 없습니다.");

        return new BoardResponseDto(boardRepository.save(new Board(requestDto , member.get().getUserid())));
    }


    public BoardResponseDto getBoardById(Long id) {
        Board board = boardRepository.findBoardByBoardId(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다"));
        return new BoardResponseDto(board);
    }

//    5. 선택한 게시글 수정 API
    @Deprecated
    @Transactional
    public BoardResponseDto updateBoardByPassword(Long id, BoardRequestDto requestDto) {

        Board board = boardRepository.findBoardByBoardId(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다"));

        try {
            //if (!board.getPassword().equals(requestDto.getPassword())) {
            //    throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
            //} else
            {
                board.update(requestDto, 0L);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return new BoardResponseDto(board);
    }

    private Board isEableBoardId(Long boardId)
    {
        return boardRepository.findBoardByBoardId(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다"));
    }
    private UserData tokenToUserdata(String token)
    {
        String pureToken = jwtUtil.substringToken(token);
        if (!jwtUtil.validateToken(pureToken))
            throw new IllegalArgumentException("Token Error");

        Optional<UserData> member = userDataRepository.findByUsername(jwtUtil.getUserInfoFromToken(pureToken).getSubject());
        if (member.isEmpty())
            throw new NoSuchElementException("해당 유저가 없습니다.");

        return member.get();
    }

    public boolean updateBoardByToken(Long boardId , BoardRequestDto requestDto, String token)
    {
        Board board = isEableBoardId(boardId);
        UserData member = tokenToUserdata(token);

        if (Objects.equals(board.getUserId(), member.getUserid()))
            board.update(requestDto, member.getUserid());
        boardRepository.save(board);

        return Objects.equals(board.getUserId(), member.getUserid());
    }

    //    6. 선택한 게시글 삭제 API
    @Deprecated
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

    public boolean deleteBoardByToken(Long boardId ,  String token)
    {
        Board board = isEableBoardId(boardId);
        UserData member = tokenToUserdata(token);

        if (Objects.equals(board.getUserId(), member.getUserid()))
            boardRepository.delete(board);

        return Objects.equals(board.getUserId(), member.getUserid());
    }// 메소드를 변수로 줘서 더 코드 줄수를 줄일수 있을듯?
}
