package com.momentree.domain.post.post.service.Impl;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.post.post.dto.request.PostRequestDto;
import com.momentree.domain.post.post.dto.response.PostResponseDto;
import com.momentree.domain.post.post.entity.Post;
import com.momentree.domain.post.post.repository.PostRepository;
import com.momentree.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    @Override
    public PostResponseDto createPost(
            CustomOAuth2User loginUser,
            PostRequestDto requestDto
    ) {
        Post post = Post.builder()
                .content(requestDto.content())
                .build();

        postRepository.save(post);

        return PostResponseDto.from(post);
    }
}
