package cn.learn.web.jwt.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * jwt-learn-cn.learn.web.jwt.auth
 *
 * @author : WXF
 * @date : 2018年-07月-10日
 */
public class CustomAuthenticationProvider implements AuthenticationProvider{

    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(userDetails != null){
            String encodePassword = DigestUtils.md5DigestAsHex(password.getBytes());
            if(userDetails.getPassword().equals(encodePassword)){
                // setter role and power
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                authorities.add(new SimpleGrantedAuthority("AUTH_WRITE"));
                // 生成令牌，令牌包含： name,password, authorities
                Authentication auth = new UsernamePasswordAuthenticationToken(username,password,authorities);
                return auth;
            }else{
                throw new BadCredentialsException("密码错误");
            }
        }else{
            throw new UsernameNotFoundException("用户不存在");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}
