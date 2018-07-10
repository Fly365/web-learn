package cn.learn.web.jwt.dao;

import cn.learn.web.jwt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * jwt-learn-cn.learn.web.jwt.dao
 *
 * @author : WXF
 * @date : 2018年-07月-09日
 */
public interface UserRespository extends JpaRepository<User,Integer> {


    User findByUsername(String username);

}
