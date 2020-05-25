package com.poppy.webmonitor;

import com.poppy.premain.MyTransformer;

import java.lang.instrument.Instrumentation;

/**
 * @description: 监听web容器的service方法
 * @author: DENGHUANQING1
 * @create: 2020-05-24 17:52
 **/
public class ServletAgent {
    /**
     * 该方法在main方法之前运行，与main方法运行在同一个JVM中
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        instrumentation.addTransformer(new WebTransformer(), true);
    }
}
