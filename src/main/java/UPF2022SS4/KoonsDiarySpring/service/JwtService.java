package UPF2022SS4.KoonsDiarySpring.service;

import antlr.Token;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    // @Value("${JWT.ISSER}") -> 배포시 들어갈 토큰 발급자
    private String ISSUER = "Test";

    // @Value("${JWT.SECRET}") -> 배포시 들어갈 토큰 해시값
    private String SECRET = "Test";

    //토큰 값 생성
    public String create(final Long id){
        try{
            JWTCreator.Builder b = JWT.create();
            b.withIssuer(ISSUER);
            b.withClaim("id",id);
            b.withExpiresAt(expiresAt());
            return b.sign(Algorithm.HMAC256(SECRET));
        }
        catch (JWTCreationException jwtCreationException ){
            log.info(jwtCreationException.getLocalizedMessage());
        }
        return null;
    }

    //만료 날짜 지정
    private Date expiresAt(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        cal.add(Calendar.HOUR, 744);
        return cal.getTime();
    }

    public static class TokenRes{
        private String token;

        public TokenRes(){

        }
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
