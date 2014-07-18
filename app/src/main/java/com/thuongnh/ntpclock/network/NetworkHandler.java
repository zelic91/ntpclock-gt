package com.thuongnh.ntpclock.network;

import android.os.AsyncTask;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by zelic on 7/18/14.
 * This class is used to get the NTP time from server
 */
public class NetworkHandler {

    private static final String TAG = NetworkHandler.class.getSimpleName();
    //NTP server name where we will get time from
    public static final String TIME_SERVER = "0.ubuntu.pool.ntp.org";

    /**
     * Callback interface for network caller
     */
    public static interface TimeCallback {
        public void success(long time);
        public void failure(Exception exception);
    }

    /**
     * Get NTP Time from server and execute callback methods
     * @param callback
     */
    public static void getNTPTime(final TimeCallback callback) {
        //Using NTPUDPClient of Apache commons net library.
        final NTPUDPClient client = new NTPUDPClient();
        client.setDefaultTimeout(3000);
        //Init network async task
        AsyncTask networkTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                TimeInfo timeInfo = null;
                try {
                    //Get time
                    InetAddress address = InetAddress.getByName(TIME_SERVER);
                    timeInfo = client.getTime(address);
                } catch (IOException e) {
                    //Error takes place, execute callback failure method
                    if (callback != null) {
                        callback.failure(e);
                    }
                }
                return timeInfo;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (o != null) {
                    //Get timeInfo from doInBackground
                    TimeInfo timeInfo = (TimeInfo) o;
                    //Extract the UNIX timestamp
                    long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
                    if (callback != null) {
                        //Callback
                        callback.success(returnTime);
                    }
                } else {
                    //Error when server return nothing
                    if (callback != null) {
                        callback.failure(new IOException("Cannot get time from NTP server."));
                    }
                }
            }
        };

        //Execute network call
        networkTask.execute();
    }
}
