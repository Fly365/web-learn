package cn.learn.web.jwt.auth;

import cn.learn.web.jwt.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * jwt-learn-cn.learn.web.jwt.auth
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法
 * attemptAuthentication ：接收并解析用户凭证。
 * successfulAuthentication ：用户成功登录后，这个方法会被调用，我们在这个方法里生成token
 * @author : WXF
 * @date : 2018年-07月-09日
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    public static final String SIGNING_KEY = "Jwt!&Secret^#";

    private AuthenticationManager authenticationManager;

    public JwtLoginFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    //接收并解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(),User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getPassword(),new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token;

        try {
            Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
            // role
            List<String> roleList = new ArrayList<>();
            for(GrantedAuthority grantedAuthority : authorities){
                roleList.add(grantedAuthority.getAuthority());
            }
            token = Jwts.builder()
                    .setSubject(authResult.getName() + "-" + roleList)
                    .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                    .signWith(SignatureAlgorithm.HS512, SIGNING_KEY)
                    .compact();
            response.addHeader("Authorization", "Bearer " + token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
