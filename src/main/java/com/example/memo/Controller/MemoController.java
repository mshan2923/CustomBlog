package com.example.memo.Controller;

import com.example.memo.entity.Memo;
import com.example.memo.entity.MemoRequestDto;
import com.example.memo.entity.MemoResponseDto;
import com.example.memo.entity.UserRequestDto;
import com.example.memo.reposition.MemoRepository;
import com.example.memo.reposition.UserRepository;
import com.example.memo.service.DB_Service;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemoController {

    //@Deprecated// Obsolete - 변경사항
    //private final Map<Long, Memo> memoList = new HashMap<>();//DB 연결 안해서

    //******** DB 접근 하는 부분 따로 모아야됨 ===> Static으로


    private  final MemoRepository memoRepository;
    //private  final UserRepository userRepository;

    public MemoController(MemoRepository memoRepository, UserRepository userRepository) {
        this.memoRepository = memoRepository;
        //this.userRepository = userRepository;
        new DB_Service(memoRepository);
    }

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        //userRepository.save()
        return DB_Service.GetInstace().OnSave(requestDto);
    }//메모 생성

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {

        // return memoRepository.findAll().stream().map(MemoResponseDto::new).toList();
        return  DB_Service.GetInstace().OnList();
        //List<Memo> 를 리턴형으로 바꾼 리스트
    }//메모 읽기 + memoList > List<MemoResponseDto>로 변환
    
    @Deprecated
    @GetMapping("/memo/{id}")// 실험용
    public  MemoResponseDto GetMemo(@PathVariable Long id)
    {
        var result = memoRepository.findById(id).get();

        System.out.println("----> Created : " + result.getCreatedAt());

        if (memoRepository.existsById(id))
            return new MemoResponseDto(result);
        else
            return null;
    }

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        return DB_Service.GetInstace().OnUpdate(id, requestDto)? id : -1L;
    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인

        System.out.println("===========> Deleted : " + requestDto.getUsername() + " / " + requestDto.getContents());

        return DB_Service.GetInstace().OnDelete(id, requestDto);
    }

}
