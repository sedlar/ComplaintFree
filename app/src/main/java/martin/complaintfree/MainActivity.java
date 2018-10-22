package martin.complaintfree;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Date last_complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendar = Calendar.getInstance();
        this.last_complaint = calendar.getTime();

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
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        Integer diff = (int) (now.getTime() - this.last_complaint.getTime()) / 1000;

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
}
