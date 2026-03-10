package dr.sbs.front.config;

import dr.sbs.front.bo.UserInfo;
import dr.sbs.front.component.GoAccessDeniedHandler;
import dr.sbs.front.component.GoAuthenticationEntryPoint;
import dr.sbs.front.component.GoAuthenticationFailureHandler;
import dr.sbs.front.component.GoAuthenticationSuccessHandler;
import dr.sbs.front.component.GoLogoutSuccessHandler;
import dr.sbs.front.service.UserService;
import dr.sbs.mp.entity.FrontUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Autowired private UserService userService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/user", "/api/user/**")
                    .authenticated()
                    // Every cross origin request will make a OPTIONS request before its real
                    // request
                    .requestMatchers(HttpMethod.OPTIONS, "/**")
                    .permitAll()
                    .anyRequest()
                    .permitAll())
        .csrf(csrf -> csrf.disable())
        .exceptionHandling(
            exception ->
                exception
                    .accessDeniedHandler(new GoAccessDeniedHandler())
                    .authenticationEntryPoint(new GoAuthenticationEntryPoint()))
        .formLogin(
            form ->
                form.loginPage("/account/login")
                    .loginProcessingUrl("/api/account/login")
                    .successHandler(new GoAuthenticationSuccessHandler())
                    .failureHandler(new GoAuthenticationFailureHandler()))
        .logout(
            logout ->
                logout
                    .logoutUrl("/api/account/logout")
                    .logoutSuccessHandler(new GoLogoutSuccessHandler())
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID"));
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    // Get logged-in user information
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FrontUser user = userService.getByUsername(username);
        if (user != null) {
          return new UserInfo(user);
        }
        throw new UsernameNotFoundException("Username or password is not correct");
      }
    };
  }
}
