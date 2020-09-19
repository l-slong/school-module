package school.module.utils;

import school.module.config.JWTConfig;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * @author Limgmk
 * @time 20-9-19 下午6:08
 * @description create jwt token for login request
 */
@Component
public class JWTUtils {

    public static final String AUTH_HEADER_KEY = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static String createJWT(String account, JWTConfig jwtConfig) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtConfig.getBase64Secret());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("account", account)
                .setIssuedAt(new Date())
                .setAudience(jwtConfig.getName()).signWith(signingKey);
        long TTLMillis = jwtConfig.getExpiresSecond() * 1000;
        if (TTLMillis >= 0) {
            long expMillis = nowMillis + TTLMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp)
                    .setNotBefore(now);
        }
        return builder.compact();
    }

    public static Claims parseJWT(String token, JWTConfig jwtConfig) {
        try {
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtConfig.getBase64Secret());
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException  eje) {
            System.out.println("token 已过期");
            return null;
        } catch (Exception e){
            System.out.println("token 解析异常:" + e.getMessage());
            return null;
        }
    }
}
