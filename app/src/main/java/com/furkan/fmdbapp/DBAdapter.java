package com.furkan.fmdbapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class DBAdapter extends RecyclerView.Adapter<DBAdapter.RatingsViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public DBAdapter(Context context,Cursor cursor){
        mContext=context;
        mCursor=cursor;
    }


    public class RatingsViewHolder extends RecyclerView.ViewHolder{

        public TextView movieNameText;
        public TextView userRatingText;


        public RatingsViewHolder( View itemView) {
            super(itemView);

            movieNameText=itemView.findViewById(R.id.movie_name);
            userRatingText=itemView.findViewById(R.id.user_rating);
        }
    }

    @Override
    public RatingsViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.movie_rating, parent, false);
        return new RatingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder( RatingsViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }

        String name=mCursor.getString(mCursor.getColumnIndex(DBContract.MovieRatingsEntry.COLUMN_TITLE));
        float rating=mCursor.getFloat(mCursor.getColumnIndex(DBContract.MovieRatingsEntry.COLUMN_RATING));
        long id = mCursor.getLong(mCursor.getColumnIndex(DBContract.MovieRatingsEntry.COLUMN_ID));

        holder.movieNameText.setText(name);
        holder.userRatingText.setText(String.valueOf(rating));
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
