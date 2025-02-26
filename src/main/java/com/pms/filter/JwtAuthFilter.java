package com.pms.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pms.config.UserInfoUserDetailsService;
import com.pms.entity.Session;
import com.pms.service.ActiveSessionService;
import com.pms.service.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoUserDetailsService userDetailsService;

    @Autowired
    private ActiveSessionService sessionService;

    public String currentUserName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        Session session = null;

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);  
                session = sessionService.getSessionBySessionId(token);

                if (session != null) {
                    token = session.getJwtToken();
                    username = jwtService.extractUsername(token);
                    currentUserName = username;
                }
            }

            // If token is present and user is not authenticated
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Check token expiration
                if (jwtService.isTokenExpired(token)) {
                    handleExpiredToken(response);
                    return; // Do not proceed further
                }

                // Load user details and validate token
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.validateToken(token, userDetails)) {
                    // Authenticate user
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    // Generate new token and update session
                    String newGeneratedToken = jwtService.generateToken(username);
                    session.setJwtToken(newGeneratedToken);
                    sessionService.saveSessionData(session);
                }
            }
        } catch (ExpiredJwtException e) {
            handleExpiredToken(response);
            return;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred during authentication.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void handleExpiredToken(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Set 401 status
        response.getWriter().write("JWT token has expired.");
    }
}
