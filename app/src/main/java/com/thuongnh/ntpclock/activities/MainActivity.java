package com.thuongnh.ntpclock.activities;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.thuongnh.ntpclock.R;
import com.thuongnh.ntpclock.customviews.ClockView;
import com.thuongnh.ntpclock.helpers.DateTimeUtil;
import com.thuongnh.ntpclock.helpers.FontUtil;
import com.thuongnh.ntpclock.network.NetworkHandler;
import com.thuongnh.zprogresshud.ZProgressHUD;

import org.w3c.dom.Text;

import java.sql.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    //Log Tag
    private static final String TAG = MainActivity.class.getSimpleName();
    //Time interval for sync timer (in milliseconds)
    private static final int SYNC_INTERVAL = 10*60*1000;

    ZProgressHUD zProgressHUD;

    //Timers
    //Sync timer handles network call
    Timer mSyncTimer;
    //Clock timer handles UI update
    Timer mClockTimer;

    //Timer tasks for timers
    TimerTask mSyncTask;
    TimerTask mClockTask;

    //Time variables
    //Current UNIX timestamp
    long mCurrentTime;
    //Time left till next sync (in milliseconds)
    long mTimeLeft;

    //Views
    TextView tvTime;
    TextView tvDate;
    TextView tvTimeLeft;
    ImageButton btnSync;

    //Clock View
    ClockView clockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = (TextView)findViewById(R.id.tv_time);
        tvDate = (TextView)findViewById(R.id.tv_date);
        tvTimeLeft = (TextView)findViewById(R.id.tv_time_left);
        btnSync = (ImageButton)findViewById(R.id.btn_sync);
        clockView = (ClockView)findViewById(R.id.clockview);

        //Styling textviews
        tvTime.setTypeface(FontUtil.getFont(this, FontUtil.LIGHT));
        tvDate.setTypeface(FontUtil.getFont(this, FontUtil.BOLD));
        tvTimeLeft.setTypeface(FontUtil.getFont(this, FontUtil.REGULAR));

        //Initial values for time variables
        mCurrentTime = System.currentTimeMillis();
        mTimeLeft = SYNC_INTERVAL;

        //Set up timers
        mClockTimer = new Timer("Clock Timer");
        mSyncTimer = new Timer("NTP Sync Timer");

        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cancel all tasks and scheduling sync again when manual sync button is clicked
                if (mSyncTask!=null) {
                    mSyncTask.cancel();
                }
                mSyncTask = new SyncTask();
                mSyncTimer.schedule(mSyncTask, 0, SYNC_INTERVAL);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        zProgressHUD = ZProgressHUD.getInstance(this);
        mSyncTask = new SyncTask();
        mSyncTimer.schedule(mSyncTask, 0, SYNC_INTERVAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Cancel all tasks
        mSyncTask.cancel();
        mSyncTimer.cancel();
        mClockTimer.cancel();
    }

    /**
     * Refresh UI after altering time values
     */
    protected void updateUI() {
        //Update UI from timers thread needs call runOnUiThread
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvTime.setText(DateTimeUtil.getDisplayTimeFromTimestamp(mCurrentTime));
                tvDate.setText(DateTimeUtil.getDisplayDateFromTimestamp(mCurrentTime));
                tvTimeLeft.setText("Next sync in: "+DateTimeUtil.getDisplayTimeFromMiliseconds(mTimeLeft));
                clockView.setTime(DateTimeUtil.getHourFromTimestamp(mCurrentTime),
                        DateTimeUtil.getMinuteFromTimestamp(mCurrentTime),
                        DateTimeUtil.getSecondFromTimestamp(mCurrentTime));
            }
        });
    }

    class ClockTask extends TimerTask {

        @Override
        public void run() {
            //Update time values and update UI per each clock tick
            mCurrentTime = DateTimeUtil.increaseTimeByOneSecond(mCurrentTime);
            mTimeLeft = DateTimeUtil.decreaseTimeByOneSecond(mTimeLeft);
            updateUI();
        }
    }

    class SyncTask extends TimerTask {
        @Override
        public void run() {
            //Displaying the progress dialog, here we have to runOnUiThread because it is called in another thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    zProgressHUD.setMessage("Sync ...");
                    zProgressHUD.show();
                }
            });

            //Get NTP time from server
            NetworkHandler.getNTPTime(new NetworkHandler.TimeCallback() {
                @Override
                public void success(long time) {
                    //Dismiss the progress dialog
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            zProgressHUD.dismissWithSuccess();
                        }
                    });
                    //Update current time
                    mCurrentTime = time;
                    //Update timeLeft
                    mTimeLeft = SYNC_INTERVAL;
                    //Update UI
                    updateUI();
                    //Restart the clock timer
                    if (mClockTask != null) {
                        mClockTask.cancel();
                    }
                    mClockTask = new ClockTask();
                    mClockTimer.schedule(mClockTask, 0, 1000);
                }

                @Override
                public void failure(Exception exception) {
                    //Display error result when cannot get result from server
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            zProgressHUD.dismissWithFailure();
                        }
                    });
                    //Reset the time left
                    mTimeLeft = SYNC_INTERVAL;
                    Log.e(TAG, exception.getMessage(), exception);
                }
            });
        }
    }
}
