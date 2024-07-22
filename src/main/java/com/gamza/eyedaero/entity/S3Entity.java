package com.gamza.eyedaero.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Entity
@RequiredArgsConstructor
@Table
@Getter
public class S3Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(unique = true, nullable = false)
    private String fileName;

}
