package com.AryanTheJavaDev.blog.SEcurity;

import com.AryanTheJavaDev.blog.Service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;

    // In JwtAuthenticationFilter class
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {  // Note the space after Bearer
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = extractToken(request);
            if (token != null) {
                try {
                    UserDetails userDetails = authenticationService.validateToken(token);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    if(userDetails instanceof BlogUserDetails) {
                        request.setAttribute("userId", ((BlogUserDetails)userDetails).getId());
                    }
                } catch (Exception e) {
                    // More detailed error logging
                    log.warn("Invalid auth token: {} - Error: {}", token.substring(0, Math.min(10, token.length())), e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("Authentication error: {}", e.getMessage(), e);
        }
        filterChain.doFilter(request, response);
    }
}
