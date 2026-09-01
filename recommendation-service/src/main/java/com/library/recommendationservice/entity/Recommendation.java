package com.library.recommendationservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Data
@Entity
@Table(name="recommendations")
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long bookId;

    @Column(nullable = false, length = 500)
    private String recommendationText;
}
