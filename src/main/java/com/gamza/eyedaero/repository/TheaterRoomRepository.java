package com.gamza.eyedaero.repository;

import com.gamza.eyedaero.entity.TheaterRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRoomRepository extends JpaRepository<TheaterRoomEntity, Long> {
}
