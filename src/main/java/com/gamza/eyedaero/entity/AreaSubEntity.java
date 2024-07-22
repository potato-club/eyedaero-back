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
public class AreaSubEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_id")
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "major_id", nullable = false)
    private AreaMajorEntity areaMajorEntity;
}
