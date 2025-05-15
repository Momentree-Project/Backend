package com.momentree.domain.post.post.service;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.post.post.dto.request.PostRequestDto;
import com.momentree.domain.post.post.dto.response.PostResponseDto;

import java.util.List;

public interface PostService {
    PostResponseDto createPost(CustomOAuth2User loginUser, PostRequestDto requestDto);
    List<PostResponseDto> getAllPosts(CustomOAuth2User loginUser);
}
