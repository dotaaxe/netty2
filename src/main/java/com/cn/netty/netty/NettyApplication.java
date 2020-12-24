package com.cn.netty.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyApplication {

    /*除了通过在@SpringBootApplication 这里调用NettyServer还可以通过实现ApplicationListener
    或者通过实现CommandLineRunner*/
    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class, args);
        run();
    }

    private static NettyServer nettyServer = new NettyServer();

    private static void run(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                nettyServer.run();
            }
        });
        thread.start();
    }

}
