package com.lambdanum.smsbackend;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FooTest {

    @Before
    public void setup() {
        System.out.println("Init foo test");
    }

    @Test
    public void fooTest() {
        assertTrue(true);
    }
}
