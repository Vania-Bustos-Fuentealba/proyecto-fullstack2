package com.library.reviewservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="reviews")
public class    Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long bookId;

    @Column(nullable = false, length = 500)
    private String comment;

    @Column(nullable = false)
    private Integer rating;

}
