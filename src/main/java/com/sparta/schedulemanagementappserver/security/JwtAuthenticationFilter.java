package com.sparta.schedulemanagementappserver.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedulemanagementappserver.dto.LoginRequestDto;
import com.sparta.schedulemanagementappserver.entity.UserRole;
import com.sparta.schedulemanagementappserver.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login");
    }

    /*
    로그인 시도 처리
    로그인 요청 데이터 읽어들여 UsernamePasswordAuthenticationToken 생성하고 인증 시도
    */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    // 로그인 성공 시 JWT 생성하고 응답 헤더에 추가 (응답 본문에 로그인 성공 메시지와 토큰 포함)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRole role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();
        String token = jwtUtil.createToken(username, role);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        //responseBody에 로그인 성공 메세지 + httpstatus 값 남기기
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("로그인 성공!\nHttpStatus : " + response.getStatus());
    }

    // 로그인 실패 시 응답 상태 401
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(401);

        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("로그인 실패!");
    }

}
