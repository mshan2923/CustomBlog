package com.example.memo.service;

import com.example.memo.entity.Memo;
import com.example.memo.entity.MemoRequestDto;
import com.example.memo.entity.MemoResponseDto;
import com.example.memo.reposition.MemoRepository;

import java.util.List;

public class DB_Service {
    private static DB_Service instance = null;

    private final MemoRepository memoRepository;

    public DB_Service(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
        if (instance == null)
            instance = this;
    }

    public static DB_Service GetInstace() {
        return instance;
    }

    public MemoResponseDto OnSave(MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto);
        memoRepository.save(memo);
        return new MemoResponseDto(memo);
    }

    public List<MemoResponseDto> OnList() {
        return memoRepository.findAll().stream().map(MemoResponseDto::new).toList();
    }

    public boolean OnUpdate(Long id , MemoRequestDto request)
    {
        if (memoRepository.existsById(id))
        {
            Memo memo = memoRepository.findById(id).get();
            memo.update(request);
            memoRepository.save(memo);

            return true;
        }else
        {
            //throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
            return false;
        }
    }

    public Long OnDelete(Long id , MemoRequestDto request)
    {
        if(memoRepository.existsById(id))
        {
            if (memoRepository.findById(id).get().getPw().equals(request.getPw()))
            {
                memoRepository.deleteById(id);
                return id;
            }else
            {
                return -1L;
            }

        }else
        {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

}
