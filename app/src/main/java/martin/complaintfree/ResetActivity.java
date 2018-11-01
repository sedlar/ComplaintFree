package martin.complaintfree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import core.ComplaintManager;

public class ResetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
    }

    public void reset(View view) {
        ComplaintManager state = ComplaintManager.getInstance(getApplicationContext());
        state.reset();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
