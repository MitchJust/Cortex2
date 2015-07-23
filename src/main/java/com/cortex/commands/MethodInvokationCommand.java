/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex.commands;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Mitchell Just <mitchell.just@bluescopesteel.com>
 */
public class MethodInvokationCommand implements CortexCommand{

    private final Object object;
    private final Method method;
    private final Object[] parameters;

    public MethodInvokationCommand(Object object, Method method, Object[] parameters) {
        this.object = object;
        this.method = method;
        this.parameters = parameters;
    }
    
    @Override
    public Object execute() {
        try {
            return method.invoke(object, parameters);
        } catch (Exception ex) {
            return ex;
        } 
    }

    @Override
    public String toString() {
        return "MethodInvokationCommand{" + "object=" + object + ", method=" + method + ", parameters=" + Arrays.toString(parameters) + '}';
    }
    
    
    
}
