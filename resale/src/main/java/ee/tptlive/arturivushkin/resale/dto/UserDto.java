package ee.tptlive.arturivushkin.resale.dto;

import ee.tptlive.arturivushkin.resale.entity.Role;
import ee.tptlive.arturivushkin.resale.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@JsonIgnoreProperties
public class UserDto {

  private Long userId;
  private String login;
  private String firstName;
  private String lastName;
  private LocalDate age;
  private String email;
  private String phone;

  private List<Role> roles;

  public User toUser(){
    User user = new User();
    user.setUserId(userId);
    user.setLogin(login);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    user.setPhone(phone);
    user.setAge(age);
    user.setRoles(roles!=null?roles: Collections.emptyList());
    return user;
  }

  public static UserDto fromUser(User user){
    return UserDto.builder()
        .userId(user.getUserId())
        .age(user.getAge())
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .login(user.getLogin())
        .lastName(user.getLastName())
        .phone(user.getPhone())
        .roles(user.getRoles())
        .build();
  }
}
