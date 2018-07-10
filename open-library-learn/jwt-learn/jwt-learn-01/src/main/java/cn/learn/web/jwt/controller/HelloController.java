package cn.learn.web.jwt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * jwt-learn-cn.learn.web.jwt.controller
 *
 * @author : WXF
 * @date : 2018年-07月-09日
 */
@RestController
public class HelloController {


    @RequestMapping("/hello")
    public String hello(){
        return "Hello JWT";
    }


}
