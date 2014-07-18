package com.thuongnh.ntpclock.network;

import junit.framework.TestCase;

import java.util.concurrent.CountDownLatch;

public class NetworkHandlerTest extends TestCase {

    public void testNTPCall() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        NetworkHandler.getNTPTime(new NetworkHandler.TimeCallback() {
            @Override
            public void success(long time) {
                assertTrue("Must be greater than 0", time > 0);
                latch.countDown();
            }

            @Override
            public void failure(Exception exception) {
                assertNotNull(exception);
                latch.countDown();
            }
        });

        latch.await();
    }
}