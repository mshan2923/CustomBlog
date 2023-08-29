package com.example.memo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.swing.*;
import java.time.LocalDateTime;

@Entity//DB , 웹통신 쓰기위해
@Getter
@Table(name = "MemoDT")
@NoArgsConstructor
public class Memo extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @Column(nullable = false)
    private  String username;

    @Column(nullable = false)
    private  String contents;


    @Column(nullable = false)
    private  String pw;    //통신시 필요

    public Memo (MemoRequestDto data)
    {
        this.username = data.getUsername();
        this.contents = data.getContents();
        this.pw = data.getPw();//통신시 필요
    }
    public void update(MemoRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.pw = requestDto.getPw();
    }
}
