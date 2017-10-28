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

    }

    @Test
    public void isUsernameValid() throws Exception {
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