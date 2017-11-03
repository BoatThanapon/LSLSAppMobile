package com.example.bearboat.lslsapp.tool;

import android.content.Context;

import com.example.bearboat.lslsapp.activity.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.*;

/**
 * Created by puttipongtadang on 10/28/17.
 */
public class ValidatorTest {

    @Test
    public void isStringEmpty() throws Exception {

        assertEquals(Validator.isStringEmpty(""),true);
        assertEquals(Validator.isStringEmpty("asdsad"),false);
        assertEquals(Validator.isStringEmpty(null),true);
    }

    @Test
    public void isUsernameValid() throws Exception {
        assertEquals(Validator.isUsernameValid("test1234"),true);
        assertEquals(Validator.isUsernameValid("test1234564897898456123456789465"),false);
        assertEquals(Validator.isUsernameValid(""),false);
    }

    @Test
    public void isPasswordValid() throws Exception {
        String validPassword = "123456789";
        String inValidPassword = "123";

        boolean validActual = Validator.isPasswordValid(validPassword);
        boolean inValidActual = Validator.isPasswordValid(inValidPassword);

        assertTrue(validActual);
        assertFalse(inValidActual);
    }

}