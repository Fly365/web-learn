package cn.learn.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class JwtAuthApplication {



    public static void main( String[] args ) {
        SpringApplication.run(JwtAuthApplication.class, args);
    }

    //密码采用了BCryptPasswordEncoder进行加密
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
