/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import java.time.ZonedDateTime;

/**
 *
 * @author Owen Tay
 */
public class AuthenticateUser {

    private static final String key = "hsianghui";
    private static final HMACSigner signer = HMACSigner.newSHA256Signer(key);

    public static String issueJwt (Long personId) {
        // Create token
        JWT jwt = new JWT().setIssuer("tutorme-admin")
                .setIssuedAt(ZonedDateTime.now());
        // Add claim to token
        jwt.addClaim("personId", personId);
        // Sign token
        String encodedJWT = JWT.getEncoder().encode(jwt, signer);
        return encodedJWT;
    }
}

// https://github.com/FusionAuth/fusionauth-jwt/blob/master/README.md