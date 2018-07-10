package cn.learn.web.jwt.service.impl;

import cn.learn.web.jwt.dao.UserRespository;
import cn.learn.web.jwt.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * jwt-learn-cn.learn.web.jwt.auth
 *
 * @author : WXF
 * @date : 2018年-07月-10日
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRespository userRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRespository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), Collections.emptyList());
    }



}
