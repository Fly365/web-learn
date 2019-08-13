package cn.learn.web.aop.learn.demo02;

/**
 * ProjectName : web-learn
 * Description : []
 * @author : Fly365
 * CreateDate : 2019年-07月-30日
 */
public class NativeWaiter implements Waiter{

    @Override
    public void greetTo(String clientName) {
        System.out.println("NativeWaiter:greet to " + clientName);
    }

    @Override
    public void servTo(String clientName) {
        System.out.println("NativeWaiter:serv to " + clientName);
    }
}
