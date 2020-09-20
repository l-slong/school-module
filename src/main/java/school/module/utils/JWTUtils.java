package school.module.utils;

import org.springframework.beans.factory.annotation.Autowired;
import school.module.Service.UserService;
import school.module.config.JWTConfig;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import school.module.entity.User;

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

    @Autowired
    private UserService userService;

    @Autowired
    private JWTConfig jwtConfig;

    public static final String AUTH_HEADER_KEY = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public String createJWT(String account) {
        User user = userService.selectByAccount(account);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        String tokenVersion = Long.toString(nowMillis);
        if(user.getTokenVersion() != null && !user.getTokenVersion().equals("")) {
            tokenVersion = user.getTokenVersion();
        } else {
            user.setTokenVersion(tokenVersion);
            userService.updateByPrimaryKeySelective(user);
        }
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtConfig.getBase64Secret());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("account", account)
                .claim("token_version", tokenVersion)
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

    public Claims parseJWT(String token) {
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
