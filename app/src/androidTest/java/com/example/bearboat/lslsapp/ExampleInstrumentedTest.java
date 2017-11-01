package com.example.bearboat.lslsapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.util.concurrent.Service;
import android.support.test.runner.AndroidJUnit4;

import com.example.bearboat.lslsapp.tool.MySharedPreference;
import com.example.bearboat.lslsapp.tool.Validator;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static android.support.test.InstrumentationRegistry.getContext;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void isConnected() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Boolean actual = Validator.isConnected(appContext);

        assertTrue(actual);
    }

    @Test
    public void isActiveOverHalfHour() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Boolean actual = Validator.isActiveOverHalfHour(appContext);

        assertFalse(actual);
    }

    @Test
    public void getPref() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        String actual = MySharedPreference.getPref(null,appContext);
        assertNull(actual);

        String actualA = MySharedPreference.getPref("1",appContext);
        assertNull(actualA);
    }


}
