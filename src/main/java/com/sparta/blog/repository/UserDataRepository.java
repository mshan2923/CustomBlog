package com.sparta.blog.repository;

import com.sparta.blog.entity.Board;
import com.sparta.blog.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
    List<UserData> findAllByOrderByModifiedAtDesc();

    Optional<UserData> findUserDataByUserid(Long id);
    Optional<UserData> findByUsername(String username);
}
