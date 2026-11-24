package com.library.contentservice.service;

import com.library.contentservice.dto.PostRequestDTO;
import com.library.contentservice.dto.PostResponseDTO;
import com.library.contentservice.entity.Post;
import com.library.contentservice.exceptions.ResourceNotFoundException;
import com.library.contentservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PostResponseDTO getPostById(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional
    public PostResponseDTO createPost(PostRequestDTO request) {
        Post post = Post.builder()
                .title(request.getTitle().trim())
                .summary(request.getSummary().trim())
                .imageUrl(clean(request.getImageUrl()))
                .imageAlt(clean(request.getImageAlt()))
                .content(request.getContent().trim())
                .build();
        Post saved = postRepository.save(post);
        log.info("Post creado con id {}", saved.getId());
        return toResponse(saved);
    }

    @Transactional
    public PostResponseDTO updatePost(Long id, PostRequestDTO request) {
        Post post = findOrThrow(id);
        post.setTitle(request.getTitle().trim());
        post.setSummary(request.getSummary().trim());
        post.setImageUrl(clean(request.getImageUrl()));
        post.setImageAlt(clean(request.getImageAlt()));
        post.setContent(request.getContent().trim());
        log.info("Post {} actualizado", id);
        return toResponse(postRepository.save(post));
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = findOrThrow(id);
        postRepository.delete(post);
        log.info("Post {} eliminado", id);
    }

    private Post findOrThrow(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un post con id " + id));
    }

    private String clean(String value) {
        return (value == null || value.isBlank()) ? null : value.trim();
    }

    private PostResponseDTO toResponse(Post post) {
        return PostResponseDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .summary(post.getSummary())
                .imageUrl(post.getImageUrl())
                .imageAlt(post.getImageAlt())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .build();
    }
}