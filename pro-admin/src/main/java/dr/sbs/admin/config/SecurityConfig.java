package dr.sbs.admin.config;

import dr.sbs.admin.component.DynamicAccessDecisionManager;
import dr.sbs.admin.component.DynamicSecurityFilter;
import dr.sbs.admin.component.DynamicSecurityMetadataSource;
import dr.sbs.admin.component.DynamicSecurityService;
import dr.sbs.admin.component.JwtAuthenticationTokenFilter;
import dr.sbs.admin.component.RestAuthenticationEntryPoint;
import dr.sbs.admin.component.RestfulAccessDeniedHandler;
import dr.sbs.admin.service.AdminResourceService;
import dr.sbs.admin.service.AdminUserService;
import dr.sbs.admin.util.JwtTokenUtil;
import dr.sbs.mp.entity.AdminResource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** 对SpringSecurity的配置的扩展，支持自定义白名单资源路径和查询用户逻辑 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
  @Autowired(required = false)
  private DynamicSecurityService dynamicSecurityService;

  @Autowired private AdminUserService userService;
  @Autowired private AdminResourceService resourceService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            auth -> {
              // 不需要保护的资源路径允许访问
              for (String url : ignoreUrlsConfig().getUrls()) {
                auth.requestMatchers(url).permitAll();
              }
              // 允许跨域请求的OPTIONS请求
              auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
              // 任何请求需要身份认证
              auth.anyRequest().authenticated();
            })
        // 关闭跨站请求防护及不使用session
        .csrf(csrf -> csrf.disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // 自定义权限拒绝处理类
        .exceptionHandling(
            exception ->
                exception
                    .accessDeniedHandler(restfulAccessDeniedHandler())
                    .authenticationEntryPoint(restAuthenticationEntryPoint()))
        // 自定义权限拦截器JWT过滤器
        .addFilterBefore(
            jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    // 有动态权限配置时添加动态权限校验过滤器
    if (dynamicSecurityService != null) {
      http.addFilterBefore(dynamicSecurityFilter(), FilterSecurityInterceptor.class);
    }

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    // 获取登录用户信息
    return username -> userService.loadUserDetailsByUsername(username);
  }

  @Bean
  public DynamicSecurityService dynamicSecurityService() {
    return new DynamicSecurityService() {
      @Override
      public Map<String, ConfigAttribute> loadDataSource() {
        Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
        List<AdminResource> resourceList = resourceService.listAll();
        for (AdminResource resource : resourceList) {
          map.put(
              resource.getUrl(),
              new org.springframework.security.access.SecurityConfig(
                  resource.getId() + ":" + resource.getName()));
        }
        return map;
      }
    };
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
    return new JwtAuthenticationTokenFilter();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
    return new RestfulAccessDeniedHandler();
  }

  @Bean
  public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
    return new RestAuthenticationEntryPoint();
  }

  @Bean
  public IgnoreUrlsConfig ignoreUrlsConfig() {
    return new IgnoreUrlsConfig();
  }

  @Bean
  public JwtTokenUtil jwtTokenUtil() {
    return new JwtTokenUtil();
  }

  @ConditionalOnBean(name = "dynamicSecurityService")
  @Bean
  public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
    return new DynamicAccessDecisionManager();
  }

  @ConditionalOnBean(name = "dynamicSecurityService")
  @Bean
  public DynamicSecurityFilter dynamicSecurityFilter() {
    return new DynamicSecurityFilter();
  }

  @ConditionalOnBean(name = "dynamicSecurityService")
  @Bean
  public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
    return new DynamicSecurityMetadataSource();
  }
}
