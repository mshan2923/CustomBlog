package com.sparta.blog.service;

import com.sparta.blog.dto.SignUpRequestDto;
import com.sparta.blog.entity.UserData;
import com.sparta.blog.repository.UserDataRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Service
public class UserDataService {
    private final UserDataRepository userDataRepository;

    public UserDataService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    public boolean isExist(SignUpRequestDto data)
    {
        //return userDataRepository.findUserDataById(id).isPresent();
        return  userDataRepository.findByUsername(data.getUsername()).isPresent();
    }

    public boolean isEnalbeId(SignUpRequestDto data)
    {
        return Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{4,10}$", data.getUsername());

    }
    public boolean isEablePw(SignUpRequestDto data)
    {
        return Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{4,10}$", data.getPassword());
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

            userDataRepository.save(new UserData(data));
            return true;
        }else
        {
            throw new ValidationException("Exist Id");
        }
    }
}
