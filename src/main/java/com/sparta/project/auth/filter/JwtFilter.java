package com.sparta.project.auth.filter;

import com.sparta.project.auth.jwt.JwtUtil;
import com.sparta.project.auth.security.PrincipalDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;


    private final PrincipalDetailsService principalDetailsService;
    public JwtFilter(JwtUtil jwtUtil, PrincipalDetailsService principalDetailsService) {
        this.jwtUtil = jwtUtil;
        this.principalDetailsService = principalDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtil.getTokenFromRequest(request);

        // Authorization 헤더에 토큰이 있는 경우
        if(StringUtils.hasText(tokenValue)){
            try {
                // "Bearer " 제거
                String token = jwtUtil.substringToken(tokenValue);

                // 서명, 만료시간, Access Token 타입 검증
                if(!jwtUtil.validateToken(token) || !jwtUtil.isAccessToken(token)){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("유효하지 않은 Access Token 입니다.");
                    return;
                }

                Claims info = jwtUtil.getUserInfoFromToken(token);

                String username = info.getSubject();

                UserDetails userDetails = principalDetailsService.loadUserByUsername(username);

                // Spring Security 인증 객체 생성
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                // 현재 요청의 인증 정보 등록
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"message\":\"유효하지 않은 인증 토큰입니다.\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);

    }
}
