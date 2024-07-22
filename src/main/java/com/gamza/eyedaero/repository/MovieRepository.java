package com.gamza.eyedaero.repository;

import com.gamza.eyedaero.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    // 부분 일치 검색을 위한 메소드
    @Query("SELECT m FROM MovieEntity m WHERE m.name LIKE %:name%")
    List<MovieEntity> findByNameContaining(@Param("name") String name);
}
