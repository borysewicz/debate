package com.example.Debate.jwt;

import com.example.Debate.service.UserPrincipalService;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends OncePerRequestFilter {
    private static final String HEADER = "Bearer";
    private TokenProvider tokenProvider;
    private UserPrincipalService userPrincipalService;

    public AuthenticationFilter(TokenProvider tokenProvider, UserPrincipalService userPrincipalService) {
        this.tokenProvider = tokenProvider;
        this.userPrincipalService = userPrincipalService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = httpServletRequest.getHeader("Authorization");
            String token = getTokenFromHeader(header);
            if (token != null){
                UsernamePasswordAuthenticationToken authenticationToken = createAuthenticationForUser(httpServletRequest, token);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private String getTokenFromHeader(String header){
        if(StringUtils.hasText(header) && header.startsWith(HEADER))
            return header.substring(7);
        else
            return null;
    }

    private UsernamePasswordAuthenticationToken createAuthenticationForUser(HttpServletRequest request, String token) {
        String login = tokenProvider.getLoginFromToken(token);
        UserDetails userDetails = userPrincipalService.loadUserByUsername(login);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }

}
