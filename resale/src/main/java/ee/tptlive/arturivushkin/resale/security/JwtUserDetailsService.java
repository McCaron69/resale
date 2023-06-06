package ee.tptlive.arturivushkin.resale.security;

import ee.tptlive.arturivushkin.resale.dao.UserRepository;
import ee.tptlive.arturivushkin.resale.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;
  public JwtUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    User user = userRepository.findByLogin(username);
    if (user != null) {
      return new JwtUserDetails(user);
    }
    throw new UsernameNotFoundException(
        "User '" + username + "' not found");
  }
}
