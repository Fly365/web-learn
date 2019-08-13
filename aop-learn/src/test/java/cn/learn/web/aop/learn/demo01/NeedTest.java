package cn.learn.web.aop.learn.demo01;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ProjectName : web-learn
 * Description : []
 * @author : Fly365
 * CreateDate : 2019年-07月-30日
 */
//注解保留期限
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NeedTest {
    //声明注解成员
    boolean value() default true;
}
