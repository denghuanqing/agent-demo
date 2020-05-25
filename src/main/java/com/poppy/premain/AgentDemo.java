package com.poppy.premain;

import java.lang.instrument.Instrumentation;

/**
 * @description: handler bytecode before Application
 * @author: DENGHUANQING1
 * @create: 2020-05-19 19:24
 **/
public class AgentDemo {
    /**
     * 该方法在main方法之前运行，与main方法运行在同一个JVM中
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        instrumentation.addTransformer(new MyTransformer(), true);
    }

}
