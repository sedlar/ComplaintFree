package core.history;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import core.ComplaintManager;
import core.db.Complaint;
import martin.complaintfree.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryItemHolder> {
    private ComplaintManager complaintManager;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class HistoryItemHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        // each data item is just a string in this case
        public TextView complaintTime;
        public HistoryItemHolder(View itemView) {
            super(itemView);
            complaintTime = itemView.findViewById(R.id.complaint_time);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info) {
            menu.add(this.getAdapterPosition(), R.id.delete, Menu.NONE, R.string.delete);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HistoryAdapter(ComplaintManager complaintManager) {
        this.complaintManager = complaintManager;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HistoryAdapter.HistoryItemHolder onCreateViewHolder(
            ViewGroup parent,  int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);

        HistoryItemHolder holder = new HistoryItemHolder(v);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(HistoryItemHolder holder, int position) {
        List<Complaint> history = complaintManager.getHistory();
        Complaint complaintItem = history.get(position);
        SimpleDateFormat format = new SimpleDateFormat();
        String complaintTime = format.format(complaintItem.getComplaint_date());
        holder.complaintTime.setText(complaintTime);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return complaintManager.getHistory().size();
    }
}
