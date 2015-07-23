/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex.console;

import com.cortex.CortexOutput;

/**
 *
 * @author Mitchell Just <mitchell.just@bluescopesteel.com>
 */
public class CortexConsoleOutput implements CortexOutput{

    @Override
    public void handleOutput(Object out) {
        System.out.println(out);
    }
    
}
