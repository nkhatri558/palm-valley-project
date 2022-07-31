/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.util;

import java.util.Random;

/**
 *
 * @author khatr
 */
public class BarcodeGenerator {

    public static String generateBarcode() {
        Random random = new Random();
        String s = new String();
        for (int i = 0; i < 3; i++) {
            char c = (char) (random.nextInt(26) + 'A');
            s += Character.toString(c);
        }
        int i = random.nextInt(1000);
        s = s + "-" + Integer.toString(i);
        return s;
    }
}
