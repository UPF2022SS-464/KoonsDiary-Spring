package UPF2022SS.KoonsDiarySpring.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static com.auth0.jwt.JWT.require;

@Slf4j
@Service
public class JwtService {

    // @Value("${JWT.ISSER}") -> 배포시 들어갈 토큰 발급자
    private String ISSUER = "Test";

    // @Value("${JWT.SECRET}") -> 배포시 들어갈 토큰 해시값
    private String SECRET_ACCESS = "TestAccess";
    private String SECRET_REFRESH = "TestRefresh";
    // access token 생성
    public String createAccessToken(final Long id){
        try{
            JWTCreator.Builder b = JWT.create();
            b.withIssuer(ISSUER);
            b.withClaim("id",id);
            b.withExpiresAt(accessExpiresAt());
            return b.sign(Algorithm.HMAC256(SECRET_ACCESS));
        }
        catch (JWTCreationException jwtCreationException ){
            log.info(jwtCreationException.getLocalizedMessage());
        }
        return null;
    }

    // refresh token 생성 메서드
    public String createRefreshToken(){
            JWTCreator.Builder b = JWT.create();
            b.withIssuer(ISSUER);
            b.withExpiresAt(refreshExpiresAt());
            return b.sign(Algorithm.HMAC256(SECRET_REFRESH));
    }

    //access 토큰 만료 날짜 지정
    private Date accessExpiresAt(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, 3);
        return cal.getTime();
    }

    //refresh 토큰 만료 날짜 지정
    private Date refreshExpiresAt(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, 744);
        return cal.getTime();
    }


    // 엑세스 토큰 해독 메서드
    public Long decodeAccessToken(final String accessToken){
        try{
            // 토큰 해독 객체 생성
            final JWTVerifier jwtVerifier = require(Algorithm.HMAC256(SECRET_ACCESS))
                    .withIssuer(ISSUER)
                    .build();

            //토큰 검증
            DecodedJWT decodedJWT = jwtVerifier.verify(accessToken);

            // 토큰 payload 반환, 고유ID 혹은 -1
            return decodedJWT.getClaim("id").asLong();
        }catch (JWTVerificationException jve){
            log.error(jve.getMessage());
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return -1L;
    }

    // 리프레시 토큰 해독 메서드
    public LocalDate decodeRefreshToken(final String refreshToken){
        try{
            // 토큰 해독 객체 생성
            final JWTVerifier jwtVerifier = require(Algorithm.HMAC256(SECRET_REFRESH))
                    .withIssuer(ISSUER)
                    .build();

            //토큰 검증
            DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
            System.out.println(" = " + decodedJWT.getExpiresAt());
            // 토큰 payload 반환, 날짜에 대한 정보
            return decodedJWT.getExpiresAt()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

        }catch (JWTVerificationException jve){
            log.error(jve.getMessage());
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return LocalDate.now();
    }

    public static class Token{
        private Long id = -1L;
        public Token(){}
        public Token(final Long user_id){
            this.id = user_id;
        }
        public Long getId(){ return this.id;}
    }

    public static class TokenRes{
        private String token;

        public TokenRes(){}
        public TokenRes(String token) {
            this.token = token;
        }
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
