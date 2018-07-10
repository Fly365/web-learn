package cn.learn.web.jwt.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * jwt-learn-cn.learn.web.jwt.domain
 *
 * @author : WXF
 * @date : 2018年-07月-09日
 */
@Data
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String username;
    private String password;




}
