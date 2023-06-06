package ee.tptlive.arturivushkin.resale.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class JwtFilter implements Filter {
  private static final String AUTHORIZATION = "Authorization";
  @Autowired
  private JwtProvider provider;

  @Autowired
  private UserDetailsService userDetailsService;


  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    String token = getTokenFromRequest((HttpServletRequest) servletRequest);
    if (token != null && provider.validateToken(token)) {
      String userLogin = provider.getLoginFromToken(token);
      UserDetails userDetails = userDetailsService.loadUserByUsername(userLogin);
      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  private String getTokenFromRequest(HttpServletRequest servletRequest) {
    String bearer = servletRequest.getHeader(AUTHORIZATION);
    if(bearer!=null && bearer.startsWith("Bearer ")){
      return bearer.substring(7);
    }
    return null;
  }
}
