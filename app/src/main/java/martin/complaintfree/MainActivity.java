package martin.complaintfree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import core.ComplaintManager;


public class MainActivity extends AppCompatActivity {

    private ComplaintManager complaintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        complaintManager = ComplaintManager.getInstance(getApplicationContext());
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
        Integer diff = this.complaintManager.getTimeFromLastComplaint();

        Integer seconds = diff % 60;
        Integer minutes = (diff / 60) % 60;
        Integer hours = (diff / 3600) % 24;
        Integer days = (diff / (86400));

        TextView secondsSinceComplaint = findViewById(R.id.seconds_since_last_complaint);
        TextView minutesSinceComplaint = findViewById(R.id.minutes_since_last_complaint);
        TextView hoursSinceComplaint = findViewById(R.id.hours_since_last_complaint);
        TextView daysSinceComplaint = findViewById(R.id.days_since_last_complaint);

        TextView secondsSinceComplaintLabel = findViewById(R.id.seconds_since_last_complaint_label);
        TextView minutesSinceComplaintLabel = findViewById(R.id.minutes_since_last_complaint_label);
        TextView hoursSinceComplaintLabel = findViewById(R.id.hours_since_last_complaint_label);
        TextView daysSinceComplaintLabel = findViewById(R.id.days_since_last_complaint_label);

        secondsSinceComplaint.setText(String.format(Locale.getDefault(), "%1$d", seconds));
        minutesSinceComplaint.setText(String.format(Locale.getDefault(), "%1$d", minutes));
        hoursSinceComplaint.setText(String.format(Locale.getDefault(), "%1$d", hours));
        daysSinceComplaint.setText(String.format(Locale.getDefault(), "%1$d", days));

        secondsSinceComplaintLabel.setText(getResources().getQuantityString(R.plurals.seconds, seconds));
        minutesSinceComplaintLabel.setText(getResources().getQuantityString(R.plurals.minutes, minutes));
        hoursSinceComplaintLabel.setText(getResources().getQuantityString(R.plurals.hours, hours));
        daysSinceComplaintLabel.setText(getResources().getQuantityString(R.plurals.days, days));
    }

    public void reportComplaint(View view) {
        complaintManager.reportComplaint();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.statistics:
                intent = new Intent(this, StatisticsActivity.class);
                startActivity(intent);
                return true;
            case R.id.history:
                intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
