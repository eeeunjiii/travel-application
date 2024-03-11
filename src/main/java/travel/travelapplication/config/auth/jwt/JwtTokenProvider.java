package travel.travelapplication.config.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Getter
public class JwtTokenProvider {

    private final long JWT_ACCESS_TOKEN_EXPTIME;
    private final long JWT_REFRESH_TOKEN_EXPTIME;
    private final String JWT_ACCESS_SECRET_KEY;
    private final String JWT_REFRESH_SECRET_KEY;
    private Key accessKey;
    private Key refreshKey;

    public JwtTokenProvider(@Value("${jwt.time.access}") long JWT_ACCESS_TOKEN_EXPTIME,
                            @Value("${jwt.time.refresh}") long JWT_REFRESH_TOKEN_EXPTIME,
                            @Value("${jwt.secret.access}") String JWT_ACCESS_SECRET_KEY,
                            @Value("${jwt.secret.refresh}") String JWT_REFRESH_SECRET_KEY) {
        this.JWT_ACCESS_TOKEN_EXPTIME = JWT_ACCESS_TOKEN_EXPTIME;
        this.JWT_REFRESH_TOKEN_EXPTIME = JWT_REFRESH_TOKEN_EXPTIME;
        this.JWT_ACCESS_SECRET_KEY = JWT_ACCESS_SECRET_KEY;
        this.JWT_REFRESH_SECRET_KEY = JWT_REFRESH_SECRET_KEY;
    }

    @PostConstruct
    public void initialize() {
        byte[] accessKeyBytes= Decoders.BASE64.decode(JWT_ACCESS_SECRET_KEY);
        this.accessKey= Keys.hmacShaKeyFor(accessKeyBytes);

        byte[] secretKeyBytes=Decoders.BASE64.decode(JWT_REFRESH_SECRET_KEY);
        this.refreshKey=Keys.hmacShaKeyFor(secretKeyBytes);
    }

    // JWT token 생성
    public String createAccessToken(ObjectId userId) {
        Claims claims= Jwts.claims().setSubject(userId.toString()); // user를 식별하는 값

        Date now=new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(new Date(now.getTime()+JWT_ACCESS_TOKEN_EXPTIME)) // 만료 시간
                .signWith(accessKey, SignatureAlgorithm.HS256) // 암호화 알고리즘
                .compact();
    }

    public String createRefreshToken(ObjectId userId) {
        Claims claims=Jwts.claims().setSubject(userId.toString());

        Date now=new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+JWT_REFRESH_TOKEN_EXPTIME))
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

}
