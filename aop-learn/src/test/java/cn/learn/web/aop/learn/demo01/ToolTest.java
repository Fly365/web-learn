package cn.learn.web.aop.learn.demo01;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * ProjectName : web-learn
 * Description : []
 * @author : Fly365
 * CreateDate : 2019年-07月-30日
 */
public class ToolTest {

    @Test
    public void tool(){
        Class clazz = ForumService.class;

        //获取相应的方法数组
        Method[] methodArr = clazz.getDeclaredMethods();
        for(Method method : methodArr){
            //获取标注注解对象
            NeedTest nt = method.getAnnotation(NeedTest.class);
            if(nt != null){
                if(nt.value()){
                    System.out.println(method.getName() + "()需要测试");
                }else{
                    System.out.println(method.getName() + "()不需要测试");
                }
            }
        }

    }




}
