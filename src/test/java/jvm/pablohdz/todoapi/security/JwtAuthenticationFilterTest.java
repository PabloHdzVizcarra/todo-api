package jvm.pablohdz.todoapi.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jvm.pablohdz.todoapi.jwtoken.JwtProvider;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest
{
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private UserDetailServiceImpl userDetailService;


    @Test
    void givenMissingBearerToken_whenApplyFilter() throws ServletException, IOException
    {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
                jwtProvider,
                userDetailService
        );
        given(request.getHeader(anyString()))
                .willReturn("errorHeader");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
    }
}