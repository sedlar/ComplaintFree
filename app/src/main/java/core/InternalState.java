package core;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;
import java.util.Date;


public class InternalState {

    private SharedPreferences preferences;
    private static final String first_started = "first_started";

    public InternalState(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Date getBeginningOfHistory() {
        Date beginningOfHistory;

        Calendar calendar = Calendar.getInstance();

        long storedBeginningOfHistory = this.preferences.getLong(this.first_started, 0);

        if (storedBeginningOfHistory != 0) {
            beginningOfHistory = new Date(storedBeginningOfHistory);
        } else {
            beginningOfHistory = calendar.getTime();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong(this.first_started, beginningOfHistory.getTime());
            editor.apply();
        }
        return beginningOfHistory;
    }

    public void reset() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(this.first_started);
        editor.apply();
    }
}
