package com.genx.mystudy.proxy.cglib;

/**
 * RealSubject
 * 真实主题类
 * @author
 * @create 2018-03-29 14:21
 **/
public class RealSubject implements ISubject {
    @Override
    public void doSomething() {
        System.out.println("RealSubject do something");
    }
}