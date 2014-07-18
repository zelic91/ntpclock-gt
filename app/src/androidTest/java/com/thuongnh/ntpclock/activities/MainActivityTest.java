package com.thuongnh.ntpclock.activities;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.TextView;

import junit.framework.TestCase;

public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {
    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent(getInstrumentation().getTargetContext(), MainActivity.class);
        startActivity(intent, null, null);
    }

    public void testInitUI(){
        assertNotNull(getActivity().clockView);
        assertNotNull(getActivity().tvDate);
        assertNotNull(getActivity().tvTime);
        assertNotNull(getActivity().tvTimeLeft);
        assertNotNull(getActivity().btnSync);
    }

}