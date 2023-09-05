package com.sparta.blog.Exception;

import com.sparta.blog.dto.SignUpRequestDto;
import com.sparta.blog.entity.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;
@Deprecated
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserData> methodArgumentValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().body(new UserData());
    }

}