package com.shooter.springboot.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * JDK新特性测试
 * */
@Slf4j
public class JDKTest {

    public static void main(String[] args) {
        log.info("ss");
        // 创建一个线程
        // 常规写法
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("常规线程："+ Thread.currentThread().getName());
            }
        }).start();
        // Lambda写法
        new Thread(()->System.out.println("Lambda线程："+ Thread.currentThread().getName())).start();
        System.out.println("主线程："+ Thread.currentThread().getName());

    }

}
