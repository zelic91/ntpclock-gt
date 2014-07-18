package com.thuongnh.ntpclock.network;

import com.thuongnh.ntpclock.Common;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by zelic on 7/18/14.
 */
public class NTPHelper {
    public static long getNTPTime() throws IOException {
        NTPUDPClient client = new NTPUDPClient();
        InetAddress address = InetAddress.getByName(Common.TIME_SERVER);
        TimeInfo timeInfo = client.getTime(address);
        long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
        return  returnTime;
    }
}
