package com.test.board.domain.repository;

import com.test.board.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitleContaining(String keyword);

    @Query("select max(b.id) from Board b")
    Long findLastID();


    void deleteByGno(Long id);
}
