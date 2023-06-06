package ee.tptlive.arturivushkin.resale.service;

import ee.tptlive.arturivushkin.resale.entity.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
  Page<User> getPage(int page);

  Optional<User> findUserById(Long id);

  User authorize(String username, String password);

  void deleteUser(Long id);

  User update(User user);

  Optional<User> create(User user);
}
