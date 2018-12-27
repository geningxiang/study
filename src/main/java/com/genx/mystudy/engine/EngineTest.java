package com.genx.mystudy.engine;

import javassist.ClassPool;
import javassist.CtClass;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/27 23:41
 */
public class EngineTest {
    public static void main(String[] args) throws IOException, ScriptException, InterruptedException {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByName("nashorn");

        engine.eval(new FileReader("D:\\idea-workspace\\MyStudy\\src\\main\\resources\\engine/socketMsgListener.js"));

        Invocable invokeEngine = (Invocable) engine;
        ISocketMsgListener l = invokeEngine.getInterface(ISocketMsgListener.class);
        StringBuffer sb = new StringBuffer();
        l.onMessage(sb, "123");
        System.out.println(sb);
        System.out.println(l.getClass().getName());
        CtClass ctclass = null;
        try {
            ctclass = ClassPool.getDefault().get(ISocketMsgListener.class.getName());// 使用全称,用于取得字节码类<使用javassist>
            System.out.println(ctclass.toString());
//            ctclass.debugWriteFile("D:\\work");
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
