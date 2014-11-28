package maleficarum.mx.dailyselfie;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import maleficarum.mx.dailyselfie.db.DbHelper;
import maleficarum.mx.dailyselfie.db.SelfieDataSource;

/**
 * @author  maleficarum [ github.com/maleficarum ]
 */
public class MainActivity extends Activity {

    private ListViewFragment fragment = null;
    private List<ListViewItem> mItems = new ArrayList<ListViewItem>();
    private final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
    private PendingIntent pendingIntent;
    private AlarmManager manager;
    private final int interval = (60 * 1000) * 2; // 2 minutes
    private SelfieDataSource dataSource ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new SelfieDataSource(this);

        try {
            dataSource.open();
        } catch (SQLException e) {
            Toast.makeText(this, "Error opening DataBase " + e.getLocalizedMessage(), Toast.LENGTH_LONG);
        }

        //Add fragment
        fragment = new ListViewFragment();
        getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();

        //Init AlarmManager
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

        Toast.makeText(this, "Loading data ...", Toast.LENGTH_LONG);

        //Load saved data; this should be done with this approach because the fragment causes NPE if does not wait a second
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        fragment.setListAdapter(new ListViewAdapter(fragment.getActivity(), dataSource.getAllItems()));
                    }});

            }
        }, 1000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_selfie) {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bp = (Bitmap) data.getExtras().get("data");

        ListViewItem item = new ListViewItem("Daily Selfie", sdf1.format(new Date()), sdf2.format(new Date()), bp);
        dataSource.createItem(item);

        fragment.setListAdapter(new ListViewAdapter(fragment.getActivity(), dataSource.getAllItems()));


    }

    public void cancelAlarm(View view) {
        if (manager != null) {
            manager.cancel(pendingIntent);
            Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
        }

    }
}
