package securityConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

//import Repository.UsersRepository;
import config.JwtService;
import entity.users;
//import stackjava.com.springmvcjwt.service.UserService;
public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
  private final static String TOKEN_HEADER = "authorization";
  @Autowired
  private JwtService jwtService;
//  @Autowired
//  private UsersRepository userrepo;
  
  
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
//    HttpServletRequest httpRequest = (HttpServletRequest) request;
//    System.out.println(request.getParameter("username"));
//    System.out.println(request.getParameter("password"));
//    String authToken = httpRequest.getHeader(TOKEN_HEADER);
//Optional<users> userOptional=userrepo.findById("1");
//boolean enabled = true;
//boolean accountNonExpired = true;
//boolean credentialsNonExpired = true;
//boolean accountNonLocked = true;
//users user=userOptional.get();
//List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//UserDetails userDetail=new User(user.getName(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,authorities);
//
//UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
//        null, authorities);
//        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//    
//    chain.doFilter(request, response);
  }
}