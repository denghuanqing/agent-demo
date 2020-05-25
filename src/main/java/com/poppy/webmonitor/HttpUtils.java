package com.poppy.webmonitor;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: DENGHUANQING1
 * @create: 2020-05-24 18:13
 **/
public class HttpUtils {
    public static void begin(Object params[]){
        HttpServletRequest param = (HttpServletRequest) params[0];
        System.out.println("======monitor======:"+param.getRequestURL());
    }
}
