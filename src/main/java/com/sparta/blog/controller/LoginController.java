package com.sparta.blog.controller;

import com.sparta.blog.Enum.UserRoleEnum;
import com.sparta.blog.Provider.JwtUtil;
import com.sparta.blog.dto.SignUpRequestDto;
import com.sparta.blog.service.UserDataService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor//final 만
@RequestMapping("/api")
public class LoginController {

    private final UserDataService userDataService;
    private  final JwtUtil jwtUtil;

    @PostMapping("/login")
    private ResponseEntity<String> OnLogin(@RequestBody SignUpRequestDto loginData, HttpServletResponse res)
    {
        //return userDataService.login(loginData);
        // Jwt 생성
        String token = jwtUtil.createToken(loginData.getUsername(), UserRoleEnum.USER);

        // Jwt 쿠키 저장
        jwtUtil.addJwtToCookie(token, res);
        //return ResponseEntity.ok(token);
        // 토큰 리턴시 탈취
        return ResponseEntity.ok("로그인 성공");//================로그인에서 존재한 회원인지 구분 없음
    }

    @PostMapping("/cookie")
    private void tryCookie(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String vaule)
    {

        System.out.println("------------ Cookie : " + vaule + " \n " + jwtUtil.getUserInfoFromToken(jwtUtil.substringToken(vaule)).getSubject());

    }


}
