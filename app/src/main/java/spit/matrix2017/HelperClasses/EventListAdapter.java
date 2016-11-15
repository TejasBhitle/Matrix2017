package spit.matrix2017.HelperClasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import spit.matrix2017.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Rohit on 13/11/16.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder> {
    private Context mContext;
    private List<Event> eventNames;
    private Event eventName;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle;
        private static final int PALETTE_SIZE = 12; /* 24 is default size. You can decrease this value to speed up palette generation */
        ImageView thumbnail;
        View background;

        MyViewHolder(View view) {
            super(view);
            eventTitle= (TextView) view.findViewById(R.id.event_title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            background =(View)view.findViewById(R.id.textView_background);
        }




//        void updatePalette() {
//
//            Bitmap bitmap = ((BitmapDrawable)thumbnail.getDrawable()).getBitmap();
//
//            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//                @Override
//                public void onGenerated(Palette palette) {
//                    //work with the palette here
//                    int defaultValue = 0x000000;
//                    int vibrant = palette.getVibrantColor(defaultValue);
////                    int vibrantLight = palette.getLightVibrantColor(defaultValue);
////                    int vibrantDark = palette.getDarkVibrantColor(defaultValue);
////                    int muted = palette.getMutedColor(defaultValue);
////                    int mutedLight = palette.getLightMutedColor(defaultValue);
////                    int mutedDark = palette.getDarkMutedColor(defaultValue);
//
//                    eventTitle.setBackgroundColor(2);
//
//                }
//            });
//
//        }


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

        eventName = eventNames.get(position);
        holder.eventTitle.setText(eventName.getName());

        Picasso.with(mContext).load(eventName.getImage()).resize(400, 400).centerCrop().into(holder.thumbnail);
        holder.thumbnail.setTag(eventName);
        holder.eventTitle.setText(eventName.getName());


        /*int lastPosition=-1;
        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;*/

    }


    @Override
    public int getItemCount() {
        return eventNames.size();
    }


}

