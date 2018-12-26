package com.genx.mystudy.proxy.cglib.asm;

import java.io.FileOutputStream;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author genx
 */
public class AsmTest extends ClassLoader implements Opcodes{

    public static void main(String []args) throws Exception
    {
        ClassWriter cw = new ClassWriter(0);
        //类名
        cw.visit(V1_7, ACC_PUBLIC, "com/genx/mystudy/proxy/cglib/asm/_766ComLeakInfo", null, "com/genx/mystudy/proxy/cglib/asm/LeakInfo", null);
        //注释
        AnnotationVisitor  av = cw.visitAnnotation("Lorg/springframework/data/mongodb/core/mapping/Document;", true);
        //注释参数
        av.visit("collection", "uc_members");
        av.visitEnd();
        //构造函数
        MethodVisitor mw = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null,null);
        mw.visitVarInsn(ALOAD, 0);
        mw.visitMethodInsn(INVOKESPECIAL, "com/genx/mystudy/proxy/cglib/asm/LeakInfo", "<init>", "()V", false);
        mw.visitInsn(RETURN);
        mw.visitMaxs(1, 1);
        mw.visitEnd();
        
        
        //字段
        FieldVisitor  fv = cw.visitField(ACC_PUBLIC, "description", "Ljava/lang/String;", null, null);
        fv.visitEnd();
        
        byte[] code = cw.toByteArray();
        
         //将二进制流写到本地磁盘上
       FileOutputStream fos = new FileOutputStream("D:/work/_766ComLeakInfo.class");
       fos.write(code);
       fos.close();

        AsmTest loader = new AsmTest();
        Class<?> clazz = loader.defineClass(null, code, 0, code.length);
        Object beanObj = clazz.getConstructor().newInstance();
        
        clazz.getField("description").set(beanObj, "Adobe客户信息泄露!");
        
        String nameString = (String) clazz.getField("description").get(beanObj);
        System.out.println("filed value : " + nameString);
    }
}