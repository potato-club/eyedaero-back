package com.gamza.eyedaero.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "areaMajorEntity")
    private List<AreaSubEntity> subs = new ArrayList<>();

}
