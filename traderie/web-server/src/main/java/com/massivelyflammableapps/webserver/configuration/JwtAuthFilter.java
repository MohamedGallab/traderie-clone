package com.massivelyflammableapps.webserver.configuration;

import com.massivelyflammableapps.shared.dto.user.UserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final UserDetailsService userDetailsService;


    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTHORIZATION);
        final String username;
        final String jwtToken;

        if(authHeader == null || !authHeader.startsWith("Bearer")) {
           filterChain.doFilter(request, response);
           return;
        }
        jwtToken = authHeader.substring(7);
        username = jwtUtils.extractUsername(jwtToken);
        if (username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
            UserDto userDetails=(UserDto) this.userDetailsService.loadUserByUsername(username);
            if (userDetails != null) {
                if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    CustomHttpServletRequestWrapper requestWrapper = new CustomHttpServletRequestWrapper(request);
                    requestWrapper.addHeader("UUID", String.valueOf(userDetails.getUser_id()));

                    // Pass the wrapped request to the next filter in the chain
                    filterChain.doFilter(requestWrapper, response);
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
