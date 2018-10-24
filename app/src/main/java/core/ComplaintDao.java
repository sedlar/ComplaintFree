package core;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ComplaintDao {
    @Query("SELECT * FROM complaint")
    List<Complaint> getAll();

    @Query("SELECT * FROM complaint ORDER BY complaint_date DESC LIMIT 1")
    Complaint lastComplaint();

    @Insert
    void complain(Complaint complaint);
}
