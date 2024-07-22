package com.gamza.eyedaero.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaMajorEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_id")
    private long id;

    private String name;

}
