package com.example.intermediate.service;

import com.example.intermediate.controller.request.CommentHeartRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.domain.*;
import com.example.intermediate.repository.CommentHeartRepository;
import com.example.intermediate.repository.CommentRepository;
import com.example.intermediate.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentHeartServiceTest {

    @Mock
    private CommentHeartRepository commentHeartRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private CommentHeartService commentHeartService;

    @Test
    void creatCommentHeart() {
        // given
        Long memberId = 1L;
        Long commentId = 2L;
        CommentHeartRequestDto requestDto = new CommentHeartRequestDto(commentId, memberId);

        Member member = new Member(1L, "nick", "pass");
        Post post = new Post();
        Comment comment = Comment.builder()
                .id(2L)
                .member(member)
                .post(post)
                .content("cont")
                .build();
        CommentHeart commentHeart = new CommentHeart(1L, member, comment);
        UserDetailsImpl userDetails = new UserDetailsImpl(member);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(commentHeartRepository.findByCommentAndMember(comment, member)).thenReturn(Optional.empty());

        // when
        ResponseDto<?> responseDto = commentHeartService.creatCommentHeart(requestDto, userDetails);

        // then
        assertEquals(responseDto.getData(), "좋아요를 등록했습니다.");
    }
}