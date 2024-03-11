package travel.travelapplication.config.auth.jwt;

public class JwtProperties {

    public static final String SECRET=""; // Jwt Token을 hash 할 때 사용할 secret key (임의로 설정)
    public static final int EXPIRATION_TIME=864000000;
    public static final String TOKEN_PREFIX="Bearer ";
    public static final String HEADER_STRING="Authorization";
}
