package com.example.intermediate.service;

import com.example.intermediate.controller.request.SubCommentHeartRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.domain.*;
import com.example.intermediate.repository.MemberRepository;
import com.example.intermediate.repository.SubCommentHeartRepository;
import com.example.intermediate.repository.SubCommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubCommentHeartServiceTest {

    @Mock
    private SubCommentHeartRepository subCommentHeartRepository;

    @Mock
    private SubCommentRepository subCommentRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private SubCommentHeartService subCommentHeartService;

    @Test
    void creatSubCommentHeart() {
        //given 사전작업

        Long subCommentId = 1L; //대댓글 아이디값
        Long memberId = 2L; //맴버의 아이디값
        SubCommentHeartRequestDto requestDto = new SubCommentHeartRequestDto(subCommentId,memberId);
        //받아오는 데이터를 저장
        Member member = new Member(memberId,"name","pw"); //맴버객체 생성초기화
        Comment comment = new Comment(); //댓글객체 생성
        SubComment subComment = SubComment.builder() //서브코멘트에 내용 저장
                .id(1L) //대댓글 아이디
                .member(member)  //맴버 객체 저장
                .comment(comment) //댓글 객체 저장
//                .content("con") // 대댓글 내용 ??
                .build(); //빌드 완료
        SubCommentHeart subCommentHeart = new SubCommentHeart(subCommentId,member,subComment); // 저장된 데이터 초기화
        UserDetailsImpl userDetails = new UserDetailsImpl(member); //유저 디테일

        //when 가짜 사용
        when(subCommentRepository.findById(subCommentId)).thenReturn(Optional.of(subComment));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(subCommentHeartRepository.findBySubCommentAndMember(subComment,member)).thenReturn(Optional.empty());

        //데이터를 서비스단에 파라미터를 줘서 받는값 responseDto에 저장
        ResponseDto<?> responseDto = subCommentHeartService.creatSubCommentHeart(requestDto,userDetails);

        assertEquals(responseDto.getData(),"좋아요를 등록했습니다.");//받아온 데이터랑 문자열이랑 비교!
    }
}