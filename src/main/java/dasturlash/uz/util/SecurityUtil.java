package dasturlash.uz.util;

import dasturlash.uz.dto.auth.JwtDTO;
import io.jsonwebtoken.JwtException;

public class SecurityUtil {

    public static JwtDTO getProfile(String token){
        try{
            String jwt = token.substring(7);
            return JwtUtil.decode(jwt);
        }catch (JwtException e){
            throw new RuntimeException("Jwt not valid");
        }
    }

    public static String getProfileId(String token){
        return getProfile(token).getId();
    }
}
