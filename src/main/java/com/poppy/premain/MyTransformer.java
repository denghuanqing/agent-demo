package com.poppy.premain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Objects;

public class MyTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        System.out.println("================= handle by javaAgent：{}");
        //java自带的方法不进行处理
        if (className.startsWith("java") || className.startsWith("sun")) {
            return classfileBuffer;
        }

        /**
         * 好像使用premain这个className是没问题的，但使用attach时className的.变成了/，所以如果是attach，那么这里需要替换
         */
        className = className.replace('/', '.');

        // 只处理ItemController类
        if (!className.endsWith("ItemController")) {
            return classfileBuffer;
        }

        try {
            ClassPool classPool = ClassPool.getDefault();

            CtClass ctClass = classPool.get(className);

            CtMethod[] declaredMethods = ctClass.getDeclaredMethods();

            for (CtMethod declaredMethod : declaredMethods) {
                // 只处理testPrint方法
                if (Objects.equals("get", declaredMethod.getName())) {

                    /**
                     * 在方法执行之前加入打印语句
                     */
                    declaredMethod.insertBefore("System.out.println(\"=====begin request=====\");");

                    /**
                     * 在方法执行之后加入打印语句
                     */
                    declaredMethod.insertAfter("System.out.println(\"=====finish request=====\");");
                }
            }

            return ctClass.toBytecode();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return classfileBuffer;
    }
}