package com.poppy.agentmain;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Objects;

/**
 * Agent  refrence:{@link https://www.throwable.club/2019/06/29/java-understand-instrument-first/}
 */

/**
 * handler bytecode in Application Running
 */
public class AgentmainAgent {

    private static Instrumentation INST;

    // 动态attach
    public static void agentmain(String agentArgs, Instrumentation inst) {
        INST = inst;
        process();
    }

    private static void process() {
        INST.addTransformer(new ClassFileTransformer() {

            @Override
            public byte[] transform(ClassLoader loader, String className,
                                    Class<?> clazz,
                                    ProtectionDomain protectionDomain,
                                    byte[] byteCode) throws IllegalClassFormatException {
                className = className.replace('/', '.');
                System.out.println(String.format("Agentmain process by ClassFileTransformer,target class = %s", className));
                ClassPool classPool = ClassPool.getDefault();

                CtClass ctClass = null;
                try {
                    ctClass = classPool.get(className);


                    CtMethod[] declaredMethods = ctClass.getDeclaredMethods();

                    for (CtMethod declaredMethod : declaredMethods) {
                        // 只处理testPrint方法
//                        if (Objects.equals("sayHello", declaredMethod.getName())) {

                            /**
                             * 在方法执行之前加入打印语句
                             */
                            declaredMethod.insertBefore("System.out.println(\"=====dynamic begin request=====\");");

                            /**
                             * 在方法执行之后加入打印语句
                             */
                            declaredMethod.insertAfter("System.out.println(\"=====dynamic finish request=====\");");
                        }
//                    }
                    System.out.println("===========enhance end===========");
                    return ctClass.toBytecode();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                return byteCode;
            }
        }, true);
        try {
            INST.retransformClasses(Class.forName("com.poppy.agentmain.AgentTargetSample"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}