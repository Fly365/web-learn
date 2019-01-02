package cn.learn.web.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * web11-learn - cn.learn.web.webflux.controller
 * 2019年-01月-02日
 * @author Fly365
 */
@Slf4j
@RestController
public class SomeHandler {

    @GetMapping("/common")
    public String commonDemo(){
        return "common response";
    }

    @GetMapping("/mono")
    public Mono<String> monoDemo(){
        return Mono.just("webflux-mono");
    }

    @GetMapping("/common2")
    public String commonDemo2(){
        log.info("common--start");
        //耗时操作
        String msg = doSome("common response");
        log.info("common--stop");
        return msg;
    }

    @GetMapping("/mono2")
    public Mono<String> monoDemo2(){
        log.info("webflux-mono--start");
        //耗时操作
        Mono<String> mono = Mono.fromSupplier(() -> doSome("webflux-mono"));
        log.info("webflux-mono-stop");
        return mono;
    }


    //为了测试效果，增加一个 相对耗时的逻辑
    private String doSome(String msg){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return msg;
    }

    //---- Mono表示处理器返回的数据为0-1个，而flux表示，处理器返回的数据为0-多个

    //数组转 flux
    @GetMapping("/flux")
    public Flux<String> fluxDemo(@RequestParam String[] hobby){
        Flux<String> flux = Flux.fromArray(hobby);
        return flux;
    }


    //集合转 flux
    @GetMapping("/flux2")
    public Flux<String> fluxDemo2(@RequestParam List<String> hobby){
        Flux<String> flux = Flux.fromStream(hobby.stream());
        return flux;
    }


    //flux底层不会阻塞处理器执行
    @GetMapping("/flux3")
    public Flux<String> fluxDemo3(@RequestParam List<String> hobby){
        log.info("flux-start");
        Flux<String> flux = Flux.fromStream(hobby.stream().map(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hobby:" + i;
        }));
        log.info("flux-stop");
        return flux;
    }


    // SSE, server-sent event,服务端推送事件
    //使用火狐浏览器看不到效果，可以使用 谷歌
    @GetMapping(value = "/flux4", produces = "text/event-stream")
    public Flux<String> fluxDemo4(@RequestParam String[] hobby){
        Flux<String> flux = Flux.fromArray(hobby);
        return flux;
    }





}
