/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex.commands;

import java.lang.reflect.Field;

/**
 *
 * @author Mitchell Just <mitchell.just@bluescopesteel.com>
 */
public class FieldAccessCommand implements CortexCommand {

    private final Field field;
    private final Object object;

    public FieldAccessCommand(Field field, Object object) {
        this.field = field;
        this.object = object;
    }
    
    @Override
    public Object execute() {
        try {
            return field.get(object);
        } catch (Exception ex) {
            return ex;
        }
    }

}
