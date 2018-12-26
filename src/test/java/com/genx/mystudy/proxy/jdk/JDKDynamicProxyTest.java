package com.genx.mystudy.proxy.jdk;

import org.junit.Test;

import javax.security.auth.Subject;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/26 22:00
 */
public class JDKDynamicProxyTest {

    @Test
    public void test() {
        // 保存生成的代理类的字节码文件
        //System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        // jdk动态代理测试
        ISubject subject = new JDKDynamicProxy(new RealSubject()).getProxy();
        subject.doSomething();
    }

}