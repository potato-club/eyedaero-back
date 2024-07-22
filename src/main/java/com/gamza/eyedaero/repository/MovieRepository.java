package com.gamza.eyedaero.repository;

import com.gamza.eyedaero.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    MovieEntity findByName(String name);
}
