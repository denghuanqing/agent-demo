package com.poppy.agentmain;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.List;

/**
 * 单独的程序执行动态attach
 */
public class AgentmainAttachMain {

    public static void main(String[] args) throws Exception {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor descriptor : list) {
            if (descriptor.displayName().endsWith("AgentTargetSample")) {
                VirtualMachine virtualMachine = VirtualMachine.attach(descriptor.id());
                virtualMachine.loadAgent("E:\\github_code\\agent-demo\\target\\agent-demo-1.0-SNAPSHOT.jar");
                virtualMachine.detach();
            }
        }
    }
}