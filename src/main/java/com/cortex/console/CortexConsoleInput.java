/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex.console;

import com.cortex.CortexInput;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mitchell Just <mitchell.just@bluescopesteel.com>
 */
public class CortexConsoleInput implements CortexInput{

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    @Override
    public String receiveInput() {
        try {
            return reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(CortexConsoleInput.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
