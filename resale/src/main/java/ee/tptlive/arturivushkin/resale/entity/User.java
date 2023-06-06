package ee.tptlive.arturivushkin.resale.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  @NotNull
  @Size(min = 2, max = 30)
  private String firstName;
  @NotNull
  @Size(min = 2, max = 30)
  private String lastName;
  @NotNull
  @Size(min = 4, max = 50)
  private String login;
  @NotNull
  @Size(min = 8, max = 256)
  @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
  private String password;
  @NotNull
  @Email
  private String email;
  private LocalDate age;
  @Pattern(regexp = "\\+\\d+")
  @Size(min = 2, max = 13)
  private String phone;
  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private List<Role> roles;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return userId == user.userId && age == user.age && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, firstName, lastName, login, password, email, age, phone);
  }
}
