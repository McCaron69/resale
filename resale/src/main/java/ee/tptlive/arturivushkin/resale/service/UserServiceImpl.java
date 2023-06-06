package ee.tptlive.arturivushkin.resale.service;

import ee.tptlive.arturivushkin.resale.dao.RoleRepository;
import ee.tptlive.arturivushkin.resale.dao.UserRepository;
import ee.tptlive.arturivushkin.resale.entity.Role;
import ee.tptlive.arturivushkin.resale.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private PasswordEncoder encoder;
  @Autowired
  UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;

  @Override
  public Page<User> getPage(int page) {
    return userRepository.findAllByOrderByLoginAsc(PageRequest.of(page, 10));
  }

  @Override
  public Optional<User> findUserById(Long id){
    return userRepository.findById(id);
  }

  @Override
  public User authorize(String username, String password) {
    User current = userRepository.findByLogin(username);
    if(current != null){
      if(encoder.matches(password,current.getPassword())) {
        return current;
      }
      else throw new CredentialsExpiredException("bad password");
    }
    throw new UsernameNotFoundException("user with name " + username + " not found");
  }

  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  public User update(final User user) {
    user.setRoles(user.getRoles().stream()
        .map(Role::getRoleId)
        .map(roleRepository::findById)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList());
    user.setPassword(userRepository
        .findById(user.getUserId())
        .orElseThrow(
            ()->new RuntimeException("cat't find and update user with id: " + user.getUserId()))
        .getPassword());
    userRepository.save(user);
    return user;
  }

  @Override
  public  Optional<User> create(User user){
    user.setPassword(encoder.encode(user.getPassword()));
    Role role = roleRepository.findByName("client");
    user.setRoles(List.of(role));
    user = userRepository.save(user);
    return Optional.ofNullable(user);
  }
}
