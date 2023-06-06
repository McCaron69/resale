package ee.tptlive.arturivushkin.resale.security;

import ee.tptlive.arturivushkin.resale.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUserDetails implements UserDetails {
  private final transient User user;
  public JwtUserDetails(User user){
    this.user = user;
  }
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getRoles().stream().map(role->new SimpleGrantedAuthority(role.getName())).toList();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getLogin();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
