package ee.tptlive.arturivushkin.resale.dto;

import ee.tptlive.arturivushkin.resale.entity.User;
import lombok.Data;

@Data
public class JwtAuthorizationDto {
  private final String token;
  private final User user;

  private final String message;

  public JwtAuthorizationDto(String token, User user, String message) {
    user.setPassword("");
    this.token = token;
    this.user = user;
    this.message = message;
  }

  public static JwtAuthorizationDto error(String message){
    return new JwtAuthorizationDto("",null,message);
  }
}
