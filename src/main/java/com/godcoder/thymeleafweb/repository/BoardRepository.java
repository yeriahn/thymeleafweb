package com.godcoder.thymeleafweb.repository;

import com.godcoder.thymeleafweb.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String Title);
    List<Board> findByTitleOrContent(String Title, String content);

    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable); //Containing = like 검색
}
