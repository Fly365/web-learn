package cn.learn.web.aop.learn.demo01;

/**
 * ProjectName : web-learn
 * Description : []
 * @author : Fly365
 * CreateDate : 2019年-07月-30日
 */
public class ForumService {

    @NeedTest(value = true)
    public void delForum(int forumId){
        System.out.println("删除论坛模块:" + forumId);
    }

    @NeedTest(value = false)
    public void delTopic(int postId){
        System.out.println("删除论坛主题:" + postId);
    }


}
