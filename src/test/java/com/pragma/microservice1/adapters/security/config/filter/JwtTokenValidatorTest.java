package com.pragma.microservice1.adapters.security.config.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pragma.microservice1.adapters.security.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtTokenValidatorTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private DecodedJWT decodedJWT;

    @Mock
    private Claim claim;

    @InjectMocks
    private JwtTokenValidator jwtTokenValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_WithValidToken() throws ServletException, IOException {
        // Arrange
        String token = "Bearer valid_token";
        String username = "testUser";
        String authorities = "ROLE_USER,ROLE_ADMIN";
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        when(jwtUtils.validateToken("valid_token")).thenReturn(decodedJWT);
        when(jwtUtils.extractUsername(decodedJWT)).thenReturn(username);
        when(jwtUtils.getSpecificClaim(decodedJWT, "authorities")).thenReturn(claim);
        when(claim.asString()).thenReturn(authorities);

        // Act
        jwtTokenValidator.doFilterInternal(request, response, filterChain);

        // Assert
        assertEquals(username, SecurityContextHolder.getContext().getAuthentication().getName());
        Collection<? extends GrantedAuthority> expectedAuthorities = List.of(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        Collection<? extends GrantedAuthority> actualAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        assertTrue(actualAuthorities.containsAll(expectedAuthorities));

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WithoutAuthorizationHeader() throws ServletException, IOException {
        // Arrange
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        // Act
        jwtTokenValidator.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtUtils, never()).validateToken(anyString());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WithInvalidToken() throws ServletException, IOException {
        // Arrange
        String token = "Bearer invalid_token";
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        doThrow(new JWTVerificationException("Invalid token")).when(jwtUtils).validateToken("invalid_token");

        // Act
        jwtTokenValidator.doFilterInternal(request, response, filterChain);

        // Assert
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }
}
