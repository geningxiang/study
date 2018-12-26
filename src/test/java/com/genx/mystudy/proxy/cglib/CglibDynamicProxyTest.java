package com.genx.mystudy.proxy.cglib;


import net.sf.cglib.proxy.Enhancer;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/26 22:24
 */
public class CglibDynamicProxyTest {
    @Test
    public void test() {
        // 保存生成的代理类的字节码文件
        //System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        // jdk动态代理测试
        ISubject subject = (ISubject) new CglibDynamicProxy(new RealSubject()).getProxy();
        subject.doSomething();
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HashMap.class);
        Object obj = enhancer.create();
        System.out.println(obj);
    }
}