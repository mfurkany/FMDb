package com.furkan.fmdbapp;

import android.database.Cursor;
import android.os.Bundle;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.furkan.fmdbapp.R.layout.activity_ratings;

public class RatingsActivity extends MainActivity {

    DBAdapter mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_ratings);
        mAdapter = new DBAdapter(this, getAllRatings());
        RecyclerView recyclerview =findViewById(R.id.recyclerViewRatings);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(mAdapter);
        mAdapter.swapCursor(getAllRatings());

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerview);

    }

    public Cursor getAllRatings(){
        return dataBase.query(
                DBContract.MovieRatingsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DBContract.MovieRatingsEntry.COLUMN_RATING+ " DESC"
        );
    }
    private void removeItem(long id) {
        dataBase.delete(DBContract.MovieRatingsEntry.TABLE_NAME,
                DBContract.MovieRatingsEntry.COLUMN_ID + "=" + id, null);
        mAdapter.swapCursor(getAllRatings());
    }

}