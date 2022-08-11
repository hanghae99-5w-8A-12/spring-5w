package com.example.intermediate.service;

import com.example.intermediate.controller.request.PostRequestDto;
import com.example.intermediate.controller.response.PostResponseDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.Post;
import com.example.intermediate.domain.UserDetailsImpl;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.CommentHeartRepository;
import com.example.intermediate.repository.CommentRepository;
import com.example.intermediate.repository.MemberRepository;
import com.example.intermediate.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private MemberRepository memberRepository;
    
    @Mock
    private HttpServletRequest request;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private PostService postService;

    @Test
    void createPost() {
        //given
        String title = "테스트"; //request내용
        String cotent="테스트내용"; //request내용
        String imageUrl = ".com"; //request내용

        PostRequestDto requestDto = new PostRequestDto(title,cotent,imageUrl);//requestDto에 담기
       
        Member member = new Member(1L,"nick","pass");
        Post post = Post.builder() //post 내용 저장
                .id(1L) //postId
                .title(title) // title
                .member(member) //member
                .content(cotent) //content
                .imageUrl(imageUrl) //imageurl
                .build();

        //when
        when(request.getHeader(anyString())).thenReturn("");//어느문자를 넣어도 가능
        when(tokenProvider.getMemberFromAuthentication()).thenReturn(member);
        when(tokenProvider.validateToken(anyString())).thenReturn(true); //anyString 어느문자열이든 가능

        ResponseDto<?> responseDto = postService.createPost(requestDto,request); //http
        
        
        assertEquals(responseDto.isSuccess(), true); //isSuccess는 getter에서 알아서 만들어줌
                                                           // boolean 값이기 때문에 true로 확인
    }
}