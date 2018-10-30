package core;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import core.db.AppDatabase;
import core.db.Complaint;


public class InternalState {

    private SharedPreferences preferences;
    private final String first_started = "first_started";
    private AppDatabase db;

    public InternalState(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        db = Room.databaseBuilder(context, AppDatabase.class, "complaints").allowMainThreadQueries().build();
    }

    private int diffTime(Date previousDate) {
        Calendar calendar = Calendar.getInstance();
        return (int) ((calendar.getTime().getTime() - previousDate.getTime()) / 1000);
    }

    public int getTimeFromLastComplaint() {
        Date beginningOfHistory;

        Calendar calendar = Calendar.getInstance();
        Complaint complaint = db.complaintsDao().lastComplaint();
        if (complaint != null) {
            return diffTime(complaint.getComplaint_date());
        }

        long storedBeginningOfHistory = this.preferences.getLong(this.first_started, 0);

        if (storedBeginningOfHistory != 0) {
            beginningOfHistory = new Date(storedBeginningOfHistory);
        } else {
            beginningOfHistory = calendar.getTime();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong(this.first_started, beginningOfHistory.getTime());
            editor.apply();
        }
        return diffTime(beginningOfHistory);
    }

    public List<Complaint> getHistory() {
        return db.complaintsDao().getAll();
    }

    public void reportComplaint() {
        Calendar calendar = Calendar.getInstance();

        Complaint complaint = new Complaint(calendar.getTime());
        db.complaintsDao().complain(complaint);
    }

    public void reset() {
        db.complaintsDao().reset();
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(this.first_started);
        editor.apply();
    }

    public void deleteItem(int position) {
        List<Complaint> history = db.complaintsDao().getAll();
        db.complaintsDao().deleteComplaint(history.get(position));
    }

    public int complaintsNumber() {
        return db.complaintsDao().getAll().size();
    }
}
