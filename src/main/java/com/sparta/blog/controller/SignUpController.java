package com.sparta.blog.controller;

import com.sparta.blog.dto.SignUpRequestDto;
import com.sparta.blog.repository.UserDataRepository;
import com.sparta.blog.service.UserDataService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SignUpController {
    private final UserDataService userDataService;

    public SignUpController(UserDataRepository userDataRepository, UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto, BindingResult bindingResult)
    {
        /*
        if (!userDataService.isEnalbeId(signUpRequestDto))
            return "사용 불가 유저이름";
        if (!userDataService.isEablePw(signUpRequestDto))
            return  "사용 불가 비밀 번호";
        */

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bindingResult.getAllErrors()");
        }


        try {
            return userDataService.registUser(signUpRequestDto)? ResponseEntity.ok("Register User")
                    : ResponseEntity.badRequest().body("Already Created");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
