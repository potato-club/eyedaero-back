package com.gamza.eyedaero.repository;


import com.gamza.eyedaero.entity.RecommendEntity;
import com.gamza.eyedaero.entity.ReviewEntity;
import com.gamza.eyedaero.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecommendRepository extends JpaRepository<RecommendEntity, Long> {
    Optional<RecommendEntity> findByUserAndReview(UserEntity user, ReviewEntity review);

}