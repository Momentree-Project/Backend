package com.momentree.domain.post.post.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * packageName    : com.momentree.domain.post.constant
 * fileName       : PostStatusEnum
 * author         : sungjun
 * date           : 2025-04-19
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-19        kyd54       최초 생성
 */
@Getter
@AllArgsConstructor
public enum PostStatusEnum {
    PUBLISHED("게시됨"),
    DELETED("삭제됨");

    private final String description;
}
