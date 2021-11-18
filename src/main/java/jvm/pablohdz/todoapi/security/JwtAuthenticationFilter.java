package jvm.pablohdz.todoapi.security;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jvm.pablohdz.todoapi.jwtoken.JwtProvider;

import static jvm.pablohdz.todoapi.security.SecurityConfig.KEY_APIKEY_PARAM;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    private final JwtProvider jwtProvider;
    private final UserDetailServiceImpl userDetailService;

    @Autowired
    public JwtAuthenticationFilter(
            JwtProvider jwtProvider,
            UserDetailServiceImpl userDetailService
    )
    {
        this.jwtProvider = jwtProvider;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException
    {
        String jwtToken = getJwtFromRequest(request);
        String apiKeyParameterValue = getApiKeyParameterValue(request);

        if (isValidToken(jwtToken))
        {
            String username = jwtProvider.getUsernameFromJwt(jwtToken);
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            if (userDetails != null)
            {
                createAuthentication(request, userDetails);
            } else
            {
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        } else if (StringUtils.hasText(apiKeyParameterValue))
        {
            Optional<UserDetails> optionalUserDetails =
                    userDetailService.loadByApiKey(apiKeyParameterValue);

            if (optionalUserDetails.isPresent())
            {
                UserDetails userDetails = optionalUserDetails.get();
                createAuthentication(request, userDetails);
            } else
            {
                SecurityContextHolder.getContext().setAuthentication(null);
            }

        } else
        {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        filterChain.doFilter(request, response);
    }

    private void createAuthentication(@NotNull HttpServletRequest request, UserDetails userDetails)
    {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities()
                );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    private String getApiKeyParameterValue(@NotNull HttpServletRequest request)
    {
        return request.getParameter(KEY_APIKEY_PARAM);
    }

    private boolean isValidToken(String jwtToken)
    {
        return StringUtils.hasText(jwtToken) && jwtProvider.validateToken(jwtToken);
    }

    private String getJwtFromRequest(@NotNull HttpServletRequest request)
    {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer"))
            return bearerToken.substring(7);

        return bearerToken;
    }
}
