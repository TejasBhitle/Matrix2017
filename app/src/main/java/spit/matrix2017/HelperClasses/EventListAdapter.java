package spit.matrix2017.HelperClasses;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import spit.matrix2017.R;

/**
 * Created by Rohit on 13/11/16.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder> {
    private Context mContext;
    private List<Event> eventNames;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle;
        ImageView thumbnail;
        View background;

        MyViewHolder(View view) {
            super(view);
            eventTitle = (TextView) view.findViewById(R.id.event_title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            background = view.findViewById(R.id.textView_background);
        }
    }

    public EventListAdapter(Context mContext, List<Event> eventNames) {
        this.mContext = mContext;
        this.eventNames = eventNames;
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_thumbnail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Event eventName = eventNames.get(position);
        holder.eventTitle.setText(eventName.getName());
        Picasso.with(mContext).load(eventName.getImage()).resize(400, 400).centerCrop().into(holder.thumbnail);

        holder.thumbnail.setTag(eventName);
        holder.eventTitle.setText(eventName.getName());

        int color = Color.parseColor(eventName.getColor());
        holder.background.setBackgroundColor(color);
        if ((Color.red(color) + Color.green(color) + Color.blue(color)) < 420) {
            holder.eventTitle.setTextColor(Color.WHITE);
        }

        /*if(position > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.up_from_bottom);
            holder.itemView.startAnimation(animation);
            lastPosition= position;
        }*/
    }

    @Override
    public int getItemCount()
    {
        return eventNames.size();
    }
}
