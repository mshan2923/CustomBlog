package com.sparta.blog.entity;

import com.sparta.blog.Security.EncoderConfig;
import com.sparta.blog.dto.SignUpRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
//@Setter
@Table(name = "userData") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class UserData extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userid;

    @Column(nullable = false)
    //@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{4,10}$", message = "조건 미충족")
    private String username;

    //@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,15}$", message = "조건 미충족")
    @Column(nullable = false)
    private String password;

    public UserData(SignUpRequestDto data)
    {
        username = data.getUsername();
        password = data.getPassword();
    }
    public UserData(SignUpRequestDto data, String newPw)
    {
        username = data.getUsername();
        password = newPw;
    }


}
