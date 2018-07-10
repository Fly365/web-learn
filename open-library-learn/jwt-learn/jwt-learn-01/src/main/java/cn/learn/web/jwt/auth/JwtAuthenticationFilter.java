package cn.learn.web.jwt.auth;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * jwt-learn-cn.learn.web.jwt.auth
 *  继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 * @author : WXF
 * @date : 2018年-07月-09日
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public static final String SIGNING_KEY = "Jwt!&Secret^#";

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            chain.doFilter(request,response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(header);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);
    }


    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token){
        String user;
        try {
            user = Jwts.parser()
                    .setSigningKey(SIGNING_KEY)
                    .parseClaimsJws(token.replace("Bearer ",""))
                    .getBody()
                    .getSubject();
            if(user != null){
                String[] splitArr = user.split("-")[1].split(",");
                List<GrantedAuthority> authorities = new ArrayList<>();
                for(String splitStr : splitArr){
                    authorities.add(new SimpleGrantedAuthority(splitStr));
                }
                return new UsernamePasswordAuthenticationToken(user,null,authorities);
            }
        } catch (ExpiredJwtException e) {
            LOGGER.error("token 已过期 {}", e);
            throw e;
        } catch (UnsupportedJwtException e) {
            LOGGER.error("token 格式错误 {}", e);
            throw e;
        } catch (MalformedJwtException e) {
            LOGGER.error("token 畸形 {}",e);
            throw e;
        } catch (SignatureException e) {
            LOGGER.error("签名失败 {}",e);
            throw e;
        } catch (IllegalArgumentException e) {
            LOGGER.error("非法参数异常 {}", e);
            throw e;
        }
        return null;
    }
}
