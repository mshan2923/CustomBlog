package com.example.memo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "User_dt")
@NoArgsConstructor
public class User {//=========== Testing (Not Use)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String pw;

    public User (UserRequestDto data)
    {
        this.id = data.getId();
        this.pw = data.getPw();
    }
}
