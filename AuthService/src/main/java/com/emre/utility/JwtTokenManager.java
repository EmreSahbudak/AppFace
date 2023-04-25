package com.emre.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {

    //private final String sifreAnahtari = "wr@M=dU4rur8splp0LvuPR_bEThutusT&q9ni3restaNlH-kOgab&wIFRobraspi";
    @Value("${my-application.jwt.secret-key-env}")
    private String secretKey;

    private Long exDate = 1000L * 60 * 2; // 2 dakika

    public Optional<String> createToken(Long id){
        String token;
        try{
            token = JWT.create().withAudience()
                    .withClaim("id", id) // Payload dataları
                    .withClaim("howtopage","AuthMicroService") // Payload dataları
                    .withClaim("isOne", true) // Payload dataları
                    .withIssuer("Java7") // token üreten uygulama
                    .withIssuedAt(new Date()) // token oluşturulma tarihi
                    .withExpiresAt(new Date(System.currentTimeMillis() + exDate)) // token in sona erme zamanı
                    .sign(Algorithm.HMAC512(secretKey)); // token imzalama algoritması
            return Optional.of(token);
        }catch (Exception e){
            return Optional.empty();
        }
    }
    //decode metodu
    public Optional<Long> getIdFromToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Java7")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if(decodedJWT==null) return Optional.empty();
            return Optional.of(decodedJWT.getClaim("id").asLong());
        }catch (Exception e){
            return Optional.empty();
        }
    }

}
