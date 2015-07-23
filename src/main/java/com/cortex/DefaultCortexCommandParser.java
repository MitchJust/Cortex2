/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex;

import com.cortex.commands.CortexCommand;
import com.cortex.commands.FieldAccessCommand;
import com.cortex.commands.MethodInvokationCommand;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mitchell Just <mitchell.just@bluescopesteel.com>
 */
public class DefaultCortexCommandParser implements CortexCommandParser {

    private final Map<String, String> searchPackages;

    public DefaultCortexCommandParser() {
        searchPackages = Collections.synchronizedMap(new HashMap<String, String>());
    }

    @Override
    public Object parseCommand(String command) throws Exception {

        //Clean up
        command = command.trim();

        System.out.println("command = " + command);

        //Check for =
        if (command.contains("=")) {
            String[] split = command.split("=");
            System.out.println("Setting " + split[0] + " to " + split[1]);
        }

        //Check if string literal        
        if (command.charAt(0) == '\"' && command.charAt(command.length() - 1) == '\"') {
            System.out.println("String Literal");
            return command.substring(1, command.length() - 1);
        }

        //Check if numeric
        try {
            return Integer.parseInt(command);
        } catch (NumberFormatException nfe) {
            //
        }
        try {
            return Double.parseDouble(command);
        } catch (NumberFormatException nfe) {
            //
        }

        if (command.charAt(command.length() - 1) == ')') {

            return parseMethodExecutionCommand(command);
        }

        //Class reference?
        try {
            return Class.forName(command);
        } catch (ClassNotFoundException ex) {
            //not a class
        }

        if (command.contains(".")) {
            //Field access

            return parseFieldAccessCommand(command);
        }

        return null;
    }

    private CortexCommand parseFieldAccessCommand(String command) throws Exception {
        System.out.println("field");

        int dotIndex = command.lastIndexOf(".");

        String objectName = command.substring(0, dotIndex);
        System.out.println("objectName = " + objectName);
        String fieldName = command.substring(dotIndex + 1);
        System.out.println("fieldName = " + fieldName);

        //Find the class
        Class clazz = Class.forName(objectName);
        Field field = clazz.getDeclaredField(fieldName);

        return new FieldAccessCommand(field, objectName);

    }

    private CortexCommand parseMethodExecutionCommand(String command) throws Exception {
        System.out.println("method");
        int parenthesesDepth = 1;
        int parenthesesStartIndex = command.length() - 1;

        //System.out.println("looking for method start");
        while (parenthesesDepth > 0) {
            char c = command.charAt(--parenthesesStartIndex);
            //System.out.println("c = " + c + ", depth = " + parenthesesDepth);
            if (c == '(') {
                parenthesesDepth--;
            }
            if (c == ')') {
                parenthesesDepth++;
            }
        }

        //System.out.println("parenthesesStartIndex = " + parenthesesStartIndex);
        //Trim off parentheses
        String parameterString = command.substring(parenthesesStartIndex + 1, command.length() - 1);
        System.out.println("parameterString = " + parameterString);

        String methodAccess = command.substring(0, parenthesesStartIndex);
        //System.out.println("methodAccess = " + methodAccess);
        int dotIndex = methodAccess.lastIndexOf(".");
        //System.out.println("dotIndex = " + dotIndex);
        String objectString = methodAccess.substring(0, dotIndex);
        System.out.println("objectString = " + objectString);
        String methodString = methodAccess.substring(dotIndex + 1);
        System.out.println("methodString = " + methodString);

        //Figure out the parameters
        List<String> parameterStrings = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean quoteEnclosed = false;
        for (int i = 0; i < parameterString.length(); i++) {
            char c = parameterString.charAt(i);

            if (c == '\"') {
                quoteEnclosed = !quoteEnclosed;
            }
            if ((c == ',' && !quoteEnclosed)) {
                String parameter = sb.toString();
                parameterStrings.add(parameter);
                sb = new StringBuilder();
            } else {
                sb.append(c);
            }
        }
        //add the last parameter
        if (sb.length() > 0) {
            parameterStrings.add(sb.toString());
        }
        Class[] parameterClasses = new Class[parameterStrings.size()];
        Object[] parameters = new Object[parameterStrings.size()];

        //Resolve the parameters
        for (int i = 0; i < parameterStrings.size(); i++) {
            String parameter = parameterStrings.get(i);
            System.out.println("parameter = " + parameter);
            Object parameterResolve = parseCommand(parameter);
            while (parameterResolve instanceof CortexCommand) {
                parameterResolve = ((CortexCommand) parameterResolve).execute();
            }

            System.out.println("parameterResolve = " + parameterResolve);

            parameters[i] = parameterResolve;
            parameterClasses[i] = parameterResolve.getClass();
        }

        //Find the Object
        Object objectResolve = parseCommand(objectString);
        while (objectResolve instanceof CortexCommand) {
            objectResolve = ((CortexCommand) objectResolve).execute();
        }

        System.out.println("objectResolve = " + objectResolve);

        //Find the method
        Method method;
        if(objectResolve instanceof Class) {
            method = ((Class)objectResolve).getDeclaredMethod(methodString, parameterClasses);
            
        } else {
            method = objectResolve.getClass().getDeclaredMethod(methodString, parameterClasses);
        }

        return new MethodInvokationCommand(objectResolve, method, parameters);
    }

    public static void main(String[] args) throws Exception {
        DefaultCortexCommandParser parser = new DefaultCortexCommandParser();
//        CortexCommand parseCommand = (CortexCommand) parser.parseCommand("java.lang.System.out.checkError()");
//        System.out.println("parseCommand = " + parseCommand);
//
//        parseCommand = (CortexCommand) parser.parseCommand("java.lang.System.out.println(\"Hi\")");
//        System.out.println("parseCommand = " + parseCommand);
        
        CortexCommand parseCommand = (CortexCommand) parser.parseCommand("java.lang.System.exit(0)");
        System.out.println("parseCommand = " + parseCommand);
        parseCommand.execute();
                
    }

}
