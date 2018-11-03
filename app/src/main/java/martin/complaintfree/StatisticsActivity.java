package martin.complaintfree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import core.ComplaintManager;
import core.db.Complaint;
import core.utils.TimeUnitsCalculator;

public class StatisticsActivity extends AppCompatActivity {

    private ComplaintManager complaintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        complaintManager = ComplaintManager.getInstance(getApplicationContext());
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
                        updateScreen();
                    }
                });
            }
        }, 0, 1000); // updates each 1 sec
    }

    private void updateScreen() {
        long beginningOfHistory = complaintManager.getBeginningOfHistory();
        List<Complaint> history = complaintManager.getHistory();
        ArrayList<Long> durations = getNoComplaintIntervals(beginningOfHistory, history);
        long currentNoComplaintTime = complaintManager.getTimeFromLastComplaint();
        long averageNoComplaintTime = getAverageNoComplaintInteval(durations);
        long medianNoComplaintTime = getMedianNoComplaintInterval(durations);
        long maxNoComplaintTime = Collections.max(durations);
        long complaintsCount = durations.size();

        TextView currentNoComplaintView = findViewById(R.id.statistics_current_field);
        TextView maxView = findViewById(R.id.statistics_max_field);
        TextView averageView = findViewById(R.id.statistics_average_field);
        TextView medianView = findViewById(R.id.statistics_median_field);
        TextView countView = findViewById(R.id.statistics_count_field);

        currentNoComplaintView.setText(formatDuration(currentNoComplaintTime));
        maxView.setText(formatDuration(maxNoComplaintTime));
        averageView.setText(formatDuration(averageNoComplaintTime));
        medianView.setText(formatDuration(medianNoComplaintTime));
        countView.setText(String.format(Locale.getDefault(), "%1$d", complaintsCount));
    }

    private String formatDuration(long duration) {
        TimeUnitsCalculator formatted_duration = new TimeUnitsCalculator(duration);

        String total = "";
        total = addStrTime(formatted_duration.getDays(), total, "d");
        total = addStrTime(formatted_duration.getHours(), total, "h");
        total = addStrTime(formatted_duration.getMinutes(), total, "m");
        total = addStrTime(formatted_duration.getSeconds(), total, "s");
        return total;
    }

    private String addStrTime(int time, String total, String unit) {
        String leadingZeros;
        if (total.equals("")) {
            leadingZeros = "1";
        } else {
            leadingZeros = "2";
        }
        String formatString = "%0" + leadingZeros + "d" + unit;
        if (time > 0 || !total.equals("")) {
            if (!total.equals("")) {
                total += " ";
            }
            total += String.format(Locale.getDefault(), formatString, time);
        }
        return total;
    }

    private ArrayList<Long> getNoComplaintIntervals(long beginningOfHistory, List<Complaint> history) {
        ArrayList<Long> durations = new ArrayList<Long>();
        Collections.reverse(history);
        long previousComplaint = beginningOfHistory;
        if (history.size() == 0) {
            durations.add((new Date().getTime() - beginningOfHistory) / 1000);
        } else {
            for (Complaint complaint: history) {
                long complaintDate = complaint.getComplaint_date().getTime();
                durations.add((complaintDate - previousComplaint) / 1000);
                previousComplaint = complaintDate;
            }
            durations.add((new Date().getTime() - previousComplaint) / 1000);
        }
        return durations;
    }

    private long getAverageNoComplaintInteval(ArrayList<Long> durations) {
        if (durations.size() == 0) {
            return 0L;
        }
        Long sum = 0L;
        for (Long duration: durations) {
            sum += duration;
        }
        return sum / durations.size();
    }

    private long getMedianNoComplaintInterval(ArrayList<Long> durations) {
        Collections.sort(durations);
        Long median;
        int size = durations.size();
        if (size == 0) {
            median = 0L;
        }else if (size % 2 == 1) {
            median = durations.get(size / 2);
        } else {
            median = (durations.get((size / 2) - 1) + durations.get(size / 2)) / 2;
        }
        return median;
    }
}
