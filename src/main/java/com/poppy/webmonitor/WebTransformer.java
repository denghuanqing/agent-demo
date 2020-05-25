package com.poppy.webmonitor;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @description: 监听基于web容器的servlet#service()方法，
 * @author: DENGHUANQING1
 * @create: 2020-05-24 18:00
 **/
public class WebTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        className = className.replace('/', '.');
        String target = "javax.servlet.http.HttpServlet";
        if (target.equals(className)){
            System.out.println("======begin===========");
            try {
                return enchanceClass(target,loader);
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CannotCompileException e) {
                e.printStackTrace();
            }
        }
        return classfileBuffer;
    }

    private byte[] enchanceClass(String target, ClassLoader loader) throws NotFoundException, IOException, CannotCompileException {
        ClassPool classPool = new ClassPool();
        classPool.insertClassPath(new LoaderClassPath(loader));
        CtClass ctClass = classPool.get(target);
        CtClass[] params = {
                classPool.get("javax.servlet.http.HttpServletRequest"),
                classPool.get("javax.servlet.http.HttpServletResponse"),
        };
        CtMethod method = ctClass.getDeclaredMethod("service", params);

        method.insertBefore("com.poppy.webmonitor.HttpUtils.begin($args);");
        /**
         * class not found【com.poppy.webmonitor.HttpUtils】
         * why ? because tomcat load class from down to up,but jvm is from up to down
         * how to solve? add below code let tomcat load this agent class {@link HttpUtils}
         */
        classPool.get("com.poppy.webmonitor.HttpUtils").toClass(loader);
        return ctClass.toBytecode();
    }
}
