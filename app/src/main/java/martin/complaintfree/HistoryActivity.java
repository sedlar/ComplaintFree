package martin.complaintfree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import core.ComplaintManager;
import core.history.HistoryAdapter;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ComplaintManager complaintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        complaintManager = ComplaintManager.getInstance(getApplicationContext());

        mRecyclerView = (RecyclerView) findViewById(R.id.history_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new HistoryAdapter(complaintManager);
        mRecyclerView.setAdapter(mAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
    int position = item.getGroupId();
    switch (item.getItemId()) {
        case R.id.delete:
            complaintManager.deleteItem(position);
            mAdapter.notifyItemRemoved(position);
            return true;
        default:
            return super.onContextItemSelected(item);
    }
}
}