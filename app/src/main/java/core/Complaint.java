package core;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Complaint {

    public Complaint(Date complaint_date) {
        this.complaint_date = complaint_date;
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name="complaint_date")
    private Date complaint_date;

    public int getUid() {
        return uid;
    }

    public Date getComplaint_date() {
        return complaint_date;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setComplaint_date(Date complaint_date) {
        this.complaint_date = complaint_date;
    }
}
