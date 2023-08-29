package com.example.memo.reposition;

import com.example.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long>
{
    //List<Users> findFirst2ByUsernameLikeOrderByIDDesc(String name);
}
