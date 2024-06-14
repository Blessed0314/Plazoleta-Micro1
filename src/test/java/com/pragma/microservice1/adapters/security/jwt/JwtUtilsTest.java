package com.pragma.microservice1.adapters.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

    @InjectMocks
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    @Mock
    private DecodedJWT decodedJWT;

    @Mock
    private Claim claim;

    @BeforeEach
    void setUp() {
        jwtUtils.privateKey = "testPrivateKey";
        jwtUtils.userGenerator = "testIssuer";
    }

    @Test
    void testCreateTokenAndValidate() {

        String expectedUsername = "testUser";
        String expectedRole = "ROLE_USER";
        String expectedDni = "123456";
        String expectedIssuer = jwtUtils.userGenerator;

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(expectedRole));
        Authentication authentication = new UsernamePasswordAuthenticationToken(expectedUsername, null, authorities);

        String token = jwtUtils.createToken(authentication, expectedDni);

        Algorithm algorithm = Algorithm.HMAC256(jwtUtils.privateKey);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(expectedIssuer)
                .build();

        DecodedJWT decodedJWT = verifier.verify(token);
        assertEquals(expectedIssuer, decodedJWT.getIssuer());
        assertEquals(expectedUsername, decodedJWT.getSubject());
        assertEquals(expectedRole, decodedJWT.getClaim("authorities").asString());
        assertEquals(expectedDni, decodedJWT.getClaim("dni").asString());
    }

    @Test
    void testValidateToken_InvalidToken() {
        // Arrange
        String token = "invalid_token";
        Algorithm algorithm = Algorithm.HMAC256(jwtUtils.privateKey);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(jwtUtils.userGenerator)
                .build();

        // Act & Assert
        assertThrows(JWTVerificationException.class, () -> verifier.verify(token));
    }

    @Test
    void testExtractUsername() {
        // Arrange
        when(decodedJWT.getSubject()).thenReturn("testUser");

        // Act
        String username = jwtUtils.extractUsername(decodedJWT);

        // Assert
        assertEquals("testUser", username);
    }

    @Test
    void testGetSpecificClaim() {
        // Arrange
        when(decodedJWT.getClaim("testClaim")).thenReturn(claim);
        when(claim.asString()).thenReturn("testValue");

        // Act
        Claim resultClaim = jwtUtils.getSpecificClaim(decodedJWT, "testClaim");

        // Assert
        assertNotNull(resultClaim);
        assertEquals("testValue", resultClaim.asString());
    }

    @Test
    void testReturnAllClaims() {
        // Arrange
        Map<String, Claim> claims = Map.of("claim1", claim);
        when(decodedJWT.getClaims()).thenReturn(claims);

        // Act
        Map<String, Claim> resultClaims = jwtUtils.returnAllClaims(decodedJWT);

        // Assert
        assertNotNull(resultClaims);
        assertEquals(claims, resultClaims);
    }
}

