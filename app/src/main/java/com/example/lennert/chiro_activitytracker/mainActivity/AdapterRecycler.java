package com.example.lennert.chiro_activitytracker.mainActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lennert.chiro_activitytracker.R;
import com.example.lennert.chiro_activitytracker.detailActivity.DetailActivity;
import com.example.lennert.chiro_activitytracker.detailActivity.SQLDBHelper;
import com.squareup.picasso.Picasso;

import java.util.List;



/**
 * Created by Lennert on 21/03/2018.
 */

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder>{

    private List<RecyclerItem> recyclerItems;
    private Context context;

    final private ListItemClickListener mOnClickListener;

    public AdapterRecycler(List<RecyclerItem> recyclerItems, Context context, ListItemClickListener Listener) {
        this.recyclerItems = recyclerItems;
        this.context = context;
        this.mOnClickListener = Listener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public String getActivity (RecyclerItem recyclerItem){
        SQLDBHelper sqldbHelper = new SQLDBHelper(context);
        SQLiteDatabase mDB = sqldbHelper.getWritableDatabase();


        String query = "SELECT name_activity FROM Chiro WHERE date = '" + recyclerItem.getSaturdayDate()+ "'";
        Cursor cursorNameActivity = mDB.rawQuery(query,null);
        if (cursorNameActivity != null && cursorNameActivity.moveToFirst()){
            String nameActivity = cursorNameActivity.getString(cursorNameActivity.getColumnIndex("name_activity"));
            cursorNameActivity.close();
            return nameActivity;
        }
        return null;
    }

    @Override
    public AdapterRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.saturday_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterRecycler.ViewHolder holder, int position) {
        RecyclerItem recyclerItem = recyclerItems.get(position);
        String activity = getActivity(recyclerItem);
        holder.mSaturdayTextView.setText(recyclerItem.getSaturdayDate());
        holder.mActivityTextView.setText(activity);

        if (activity == null){
            holder.mCardview.setBackgroundColor(context.getResources().getColor(R.color.topperRed));
        } else{
            holder.mCardview.setBackgroundColor(context.getResources().getColor(R.color.rakkerGreen));
        }
        //holder.mWeatherSymbol.setBackgroundColor(context.getResources().getColor(R.color.white));
        holder.mTemperatureTextView.setText(recyclerItem.getTemperature());
        holder.mActivityTextView.setTextColor(context.getResources().getColor(R.color.white));
        Picasso.with(context).load(recyclerItem.getWeather()).into(holder.mWeatherSymbol);
    }

    @Override
    public int getItemCount() {
        return recyclerItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView mSaturdayTextView;
        public ImageView mWeatherSymbol;
        public TextView mTemperatureTextView;
        public TextView mActivityTextView;
        public ConstraintLayout mCardview;

        public ViewHolder(View itemView) {
            super(itemView);

            mSaturdayTextView = itemView.findViewById(R.id.tv_saturday);
            mWeatherSymbol = itemView.findViewById(R.id.weatherSymbol);
            mTemperatureTextView = itemView.findViewById(R.id.tv_temperature);
            mActivityTextView = itemView.findViewById(R.id.tv_activity);
            mCardview = itemView.findViewById(R.id.rl_cardview);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);


        }
    }


}
