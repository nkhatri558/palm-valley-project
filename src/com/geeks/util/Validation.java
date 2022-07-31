/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.util;

/**
 *
 * @author khatr
 */
public class Validation {
    public static Boolean validateNIC(String nic) {
        return nic.matches("[0-9]{5}[-][0-9]{7}[-][0-9]{1}");
    }
    public static Boolean validateContact(String contact) {
        return contact.matches("[0-9]{4}[-][0-9]{7}");
    }
}
