package com.lambdanum.smsbackend;

import com.lambdanum.smsbackend.sms.SmsProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class FooTest {

    @Mock
    private SmsProvider smsProvider;

    @Before
    public void setup() {
        System.out.println("Init foo test");
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fooTest() {
        assertTrue(true);
    }
}
