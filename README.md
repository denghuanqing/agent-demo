# agent-demo
javaaggent demo - use javaagent and javassist to enhance bytecode

# how to use
### run before application
1.update pom.xml
```$xslt
<Premain-Class>com.poppy.premain.AgentDemo</Premain-Class>
```
2.package
```$xslt
mvn clean install
```
3.set agent
```$xslt
java -javaagent:E:\github_code\agent-demo\target\agent-demo-1.0-SNAPSHOT.jar -jar ***.jar
```
### run in application running
1.update pom.xml
```$xslt
<Agent-Class>com.poppy.agentmain.AgentmainAgent</Agent-Class>
```
2.package
```$xslt
mvn clean install
```
3.run target and dynamic attach
```$xslt
run com.poppy.agentmain.AgentTargetSample#main()
run com.poppy.agentmain.AgentmainAttachMain#main()
```

# reference
[instrumentation 功能介绍](https://my.oschina.net/robinyao/blog/489767)

[Javassist中文技术文档](https://www.cnblogs.com/scy251147/p/11100961.html)
