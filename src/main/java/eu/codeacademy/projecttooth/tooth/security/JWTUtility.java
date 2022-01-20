package eu.codeacademy.projecttooth.tooth.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import eu.codeacademy.projecttooth.tooth.dto.UserDto;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.model.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Data
public class JWTUtility {

    @Value("${auth.secret}")
    private String secret;

    @Value("${auth.accessTokenTime}")
    private Integer accessTokenTime;

    @Value("${auth.refreshTokenTime}")
    private Integer refreshTokenTime;

    @Value("${auth.headerPrefix}")
    private String headerPrefix;

    private Algorithm algorithm;

    public Algorithm getAlgorithm() {
        return algorithm = Algorithm.HMAC256(secret.getBytes());
    }

    public String accessTokenForPrincipal(UserPrincipal user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenTime))
//                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withClaim("userId", user.getUserId())
                .sign(algorithm);
    }

    public String refreshTokenForPrincipal(UserPrincipal user, HttpServletRequest request) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenTime))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withClaim("userId", user.getUserId())
                .sign(algorithm);
    }

    public String accessTokenForUser(UserDto user, HttpServletRequest request) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenTime))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", new ArrayList<>(){{add(user.getRole());}})
                .withClaim("userId", user.getUserId())
                .sign(algorithm);
    }

    public DecodedJWT decodeJWT(String authorizationHeader) {
        String token = authorizationHeader.substring(headerPrefix.length()).trim();
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }

    public DecodedJWT decodeWithoutPrefix(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }
}