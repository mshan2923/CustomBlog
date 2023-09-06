package com.sparta.blog.service;

import com.sparta.blog.Security.EncoderConfig;
import com.sparta.blog.dto.SignUpRequestDto;
import com.sparta.blog.entity.UserData;
import com.sparta.blog.repository.UserDataRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@RequiredArgsConstructor
@Service
public class UserDataService {
    private final UserDataRepository userDataRepository;
    private final EncoderConfig encoder;


    public boolean isExist(SignUpRequestDto data)
    {
        return  userDataRepository.findByUsername(data.getUsername()).isPresent();
    }

    public boolean isEnalbeId(SignUpRequestDto data)
    {
        return Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{4,10}$", data.getUsername());

    }
    public boolean isEablePw(SignUpRequestDto data)
    {
        return Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,15}$", data.getPassword());
    }
    @Transactional
    public boolean registUser(SignUpRequestDto data)
    {
        if (!isExist(data))//대소문자 구별 없음
        {
            if (!isEnalbeId(data))
                throw new ValidationException("Wrong pattern id");
            if (!isEablePw(data))
                throw new ValidationException("Wrong pattern password");

            userDataRepository.save(new UserData(data , encoder.Crypt(data.getPassword())));
            return true;
        }else
        {
            throw new ValidationException("Exist Id");
        }
    }
    public boolean login(SignUpRequestDto data)
    {
        var target = userDataRepository.findByUsername(data.getUsername());
        if (target.isPresent())
            return encoder.matches(data.getPassword(), target.get().getPassword());
        else
            throw new NoSuchElementException("해당 유저가 존재하지 않습니다.");
    }
}
