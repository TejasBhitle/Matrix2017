package spit.matrix2017.HelperClasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;

import spit.matrix2017.R;


public class EventListAdapter extends
        RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    Context context;
    ArrayList<String> arrayList;



    public EventListAdapter(ArrayList<String> list, Context c){
        arrayList=list;
        context=c;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.textView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View customView = layoutInflater.inflate(R.layout.temp_event_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(customView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String string = arrayList.get(position);
        holder.name.setText(string);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
