package com.library.contentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDTO {

    private Long id;
    private String title;
    private String summary;
    private String imageUrl;
    private String imageAlt;
    private String content;
    private LocalDateTime createdAt;
}