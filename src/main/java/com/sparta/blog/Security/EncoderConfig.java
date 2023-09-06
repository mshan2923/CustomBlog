package com.sparta.blog.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class EncoderConfig {
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public String Crypt(String data)
    {
        return encoder().encode(data);
    }
    public boolean matches(String rawData, String ecodeData)
    {
        return encoder().matches(rawData, ecodeData);
    }
}