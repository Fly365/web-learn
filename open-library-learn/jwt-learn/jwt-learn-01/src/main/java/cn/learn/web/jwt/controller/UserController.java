package cn.learn.web.jwt.controller;

import cn.learn.web.jwt.dao.UserRespository;
import cn.learn.web.jwt.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * jwt-learn-cn.learn.web.jwt.controller
 * 这里测试测试，没有复杂的业务逻辑，所以直接调取了DAO而省略了service成
 * @author : WXF
 * @date : 2018年-07月-09日
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户相关",description = "用户处理，测试不做抽取分割")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //该方法是注册用户的方法，默认放开访问控制
    @ApiOperation(value = "用户注册")
    @PostMapping("/signup")
    public Map<String,Object> signUp(@RequestBody User user){
        User bizUser = userRespository.findByUsername(user.getUsername());
        Map<String,Object> result = new HashMap<>();
        if(bizUser != null){
            result.put("msg","用户已存在");
            return result;
        }
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword()).getBytes()));
        userRespository.save(user);
        result.put("msg","用户注册成功");
        return result;
    }

    @ApiOperation(value = "查询用户权限")
    @RequestMapping("/authorities")
    public List<String> getAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> authenticationList = new ArrayList<>();
        for(GrantedAuthority grantedAuthority : authorities){
            LOGGER.info("权限列表 {}", grantedAuthority.getAuthority());
            authenticationList.add(grantedAuthority.getAuthority());
        }
        return authenticationList;
    }

    @RequestMapping("/")
    @ApiOperation(value = "用户查询")
    public Map<String,Object> getUserList(){
        List<User> userList = userRespository.findAll();
        LOGGER.info("用户列表 {} ", userList);
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("users", userList);
        return userMap;
    }





}
