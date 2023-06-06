package ee.tptlive.arturivushkin.resale.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {
  @Value("${jwt.token.secret}")
  private String jwsSecret;

  public String generateToken(String username) {
    Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
    return Jwts.builder()
        .setSubject(username)
        .setExpiration(date)
        .signWith(SignatureAlgorithm.HS512, jwsSecret)
        .compact();
  }

  public boolean validateToken(String token){
    try{
      Jwts.parser().setSigningKey(jwsSecret).parseClaimsJws(token);
      return true;
    } catch (Exception e){
      log.error("token validating error");
    }
    return false;
  }

  public String getLoginFromToken(String token){
    Claims claims = Jwts.parser().setSigningKey(jwsSecret).parseClaimsJws(token).getBody();
    return claims.getSubject();
  }
}
