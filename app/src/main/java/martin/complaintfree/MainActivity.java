package martin.complaintfree;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import core.InternalState;


public class MainActivity extends AppCompatActivity {

    private InternalState internalState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        internalState = new InternalState(getApplicationContext());
        updateTime();
    }

    @Override
    public void onResume() {
     super.onResume();
     Timer autoUpdate = new Timer();
     autoUpdate.schedule(new TimerTask() {
      @Override
      public void run() {
       runOnUiThread(new Runnable() {
        public void run() {
          updateTime();
         }
        });
       }
      }, 0, 1000); // updates each 1 sec
     }

    private void updateTime() {
        Integer diff = this.internalState.getTimeFromLastComplaint();

        Integer seconds = diff % 60;
        Integer minutes = (diff / 60) % 60;
        Integer hours = (diff / 3600) % 60;
        Integer days = (diff / (86400));

        TextView secondsSinceComplaint = findViewById(R.id.seconds_since_last_complaint);
        TextView minutesSinceComplaint = findViewById(R.id.minutes_since_last_complaint);
        TextView hoursSinceComplaint = findViewById(R.id.hours_since_last_complaint);
        TextView daysSinceComplaint = findViewById(R.id.days_since_last_complaint);

        secondsSinceComplaint.setText(getResources().getQuantityString(R.plurals.seconds, seconds, seconds));
        minutesSinceComplaint.setText(getResources().getQuantityString(R.plurals.minutes, minutes, minutes));
        hoursSinceComplaint.setText(getResources().getQuantityString(R.plurals.hours, hours, hours));
        daysSinceComplaint.setText(getResources().getQuantityString(R.plurals.days, days, days));
    }

    public void reportComplaint(View view) {
        internalState.reportComplaint();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                updateTime();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
