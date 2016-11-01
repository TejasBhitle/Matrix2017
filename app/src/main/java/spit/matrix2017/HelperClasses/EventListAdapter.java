package spit.matrix2017.HelperClasses;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import spit.matrix2017.Activities.EventDetails;
import spit.matrix2017.R;


public class EventListAdapter extends
        RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> arrayList;

    public EventListAdapter(ArrayList<String> list, Context c) {
        arrayList = list;
        context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View customView = layoutInflater.inflate(R.layout.temp_event_layout, parent, false);
        return new ViewHolder(customView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String string = arrayList.get(position);
        holder.name.setText(string);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EventDetails.class);
                i.putExtra("EVENT_NAME", string);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
