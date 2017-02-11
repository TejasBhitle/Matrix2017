package spit.matrix2017.HelperClasses;

/**
 * Created by akshay on 11-02-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import spit.matrix2017.R;

public class SponsorRecyclerAdapter extends RecyclerView.Adapter<SponsorRecyclerAdapter.ViewHolder> {


        Context context;

        ArrayList<String> arrayList;
        Integer[] drawableList;

        public SponsorRecyclerAdapter(ArrayList<String> arrayList, Integer[] drawableList) {
            this.arrayList = arrayList;
            this.drawableList = drawableList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_child,parent,false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.info.setText(String.format(arrayList.get(position)));
            holder.imageView.setImageResource(drawableList[position]);
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView info;
            ImageView imageView;


            public ViewHolder(View itemView) {
                super(itemView);
                context = itemView.getContext();
                info = (TextView)itemView.findViewById(R.id.info_text);
                imageView = (ImageView)itemView.findViewById(R.id.imageView);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if(getLink()=="NULL")
                    Toast.makeText(context,"Webpage not available for selected sponsor",Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(context, web.class);
                    intent.putExtra("SITE", getLink());
                    context.startActivity(intent);
                }
            }

            String getLink(){
                String link="";

                if(getAdapterPosition()==0)
                    link = "http://www.tcs.com/m/default.aspx";
                else if(getAdapterPosition()==1)
                    link = "NULL";
                else if(getAdapterPosition()==2)
                    link = "NULL";
                else if(getAdapterPosition()==3)
                    link = "http://maharashtratimes.indiatimes.com";
                else if(getAdapterPosition()==4)
                    link = "http://www.brainheaters.in";
                else if(getAdapterPosition()==5)
                    link = "http://www.careers360.com";
                else if(getAdapterPosition()==6)
                    link = "http://www.scotlanejeans.com";
                else if(getAdapterPosition()==7)
                    link = "https://internshala.com";
                else if(getAdapterPosition()==8)
                    link = "http://www.nactus.com";
                else if(getAdapterPosition()==9)
                    link = "https://www.rentsetgo.co";

                return link;
            }

        }
}
