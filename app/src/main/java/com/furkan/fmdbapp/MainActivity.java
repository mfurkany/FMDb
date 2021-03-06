package com.furkan.fmdbapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase dataBase;
    TextView tvYear,tvCast,tvRating,tvPlot,tvRuntime;
    ImageView moviePoster;
    EditText search_bar;
    Button button,rateTextButton;
    RequestQueue mQueue;
    RatingBar ratingBar;
    String movieTitle;
    float userRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper= new DBHelper(this);
        dataBase=dbHelper.getWritableDatabase();

        search_bar=findViewById(R.id.search_bar);
        tvYear=findViewById(R.id.tvYear);
        tvCast=findViewById(R.id.tvCast);
        tvRating=findViewById(R.id.tvRating);
        tvPlot=findViewById(R.id.tvPlot);
        moviePoster=findViewById(R.id.moviePoster);
        tvRuntime=findViewById(R.id.tvRuntime);
        button=(findViewById(R.id.button));
        rateTextButton=(findViewById(R.id.rateTextButton));
        ratingBar=(findViewById(R.id.ratingBar));
        mQueue= Volley.newRequestQueue(this);

        rateTextButton.setVisibility(View.INVISIBLE);
        ratingBar.setVisibility(View.INVISIBLE);

        rateTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRating=ratingBar.getRating();
                addToDb(movieTitle,userRating);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });

    }
    private void jsonParse(){
        String mName=search_bar.getText().toString();
        String[] movieNameWords=mName.split(" ");
        String url=" ";
        int wordCountofMovieName=movieNameWords.length;
        if(wordCountofMovieName==1){
            url = "https://www.omdbapi.com/?t="+mName+"&plot=full&apikey=7693bcee";
        }else if(wordCountofMovieName==2){
            url="https://www.omdbapi.com/?t="+movieNameWords[0]+"+"+movieNameWords[1]+"&plot=full&apikey=7693bcee";
        }else if(wordCountofMovieName==3){
            url="https://www.omdbapi.com/?t="+movieNameWords[0]+"+"+movieNameWords[1]+"+"+movieNameWords[2]+"&plot=full&apikey=7693bcee";
        }else if (wordCountofMovieName==4){
            url="https://www.omdbapi.com/?t="+movieNameWords[0]+"+"+movieNameWords[1]+"+"+movieNameWords[2]+"+"+movieNameWords[3]+"&plot=full&apikey=7693bcee";
        }else if (wordCountofMovieName==5) {
            url="https://www.omdbapi.com/?t="+movieNameWords[0]+"+"+movieNameWords[1]+"+"+movieNameWords[2]+"+"+movieNameWords[3]+"+"+movieNameWords[4]+"&plot=full&apikey=7693bcee";
        }

        StringRequest request=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject movie=new JSONObject(response);
                            String result=movie.getString("Response");

                            if (result.equals("True")){
                                Toast.makeText(MainActivity.this,"found", Toast.LENGTH_SHORT).show();
                                Spannable sYear = new SpannableString("year   ");   //year
                                Spannable sRating = new SpannableString ("imdb rating   ");
                                Spannable sCast = new SpannableString("cast   ");
                                Spannable sRuntime = new SpannableString("runtime   ");
                                Spannable sPlot = new SpannableString("plot   ");
                                sYear.setSpan(new ForegroundColorSpan(Color.parseColor("#FF5722")),0, sYear.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                sRating.setSpan(new ForegroundColorSpan(Color.parseColor("#FF5722")),0, sRating.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                sCast.setSpan(new ForegroundColorSpan(Color.parseColor("#FF5722")),0, sCast.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                sRuntime.setSpan(new ForegroundColorSpan(Color.parseColor("#FF5722")),0, sRuntime.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                sPlot.setSpan(new ForegroundColorSpan(Color.parseColor("#FF5722")),0, sPlot.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                movieTitle=movie.getString("Title");
                                String year=movie.getString("Year");
                                tvYear.setText(sYear);
                                tvYear.append(year);
                                String rating=movie.getString("imdbRating");
                                tvRating.setText(sRating);
                                tvRating.append(rating);
                                String cast=movie.getString("Actors");
                                tvCast.setText(sCast);
                                tvCast.append(cast);
                                String plot=movie.getString("Plot");
                                tvPlot.setText(sPlot);
                                tvPlot.append(plot);
                                String runTime=movie.getString("Runtime");
                                tvRuntime.setText(sRuntime);
                                tvRuntime.append(runTime);
                                rateTextButton.setText("rate movie");
                                rateTextButton.setVisibility(View.VISIBLE);
                                ratingBar.setVisibility(View.VISIBLE);
                                String posterUrl=movie.getString("Poster");
                                if(posterUrl.equals("N/A")){
                                }
                                else{
                                    Picasso.get().load(posterUrl).into(moviePoster);
                                }

                            }
                            else {
                                Toast.makeText(MainActivity.this,"movie not found",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    public void addToDb(String title,float rating){
        if(userRating!=0){
            ContentValues cv=new ContentValues();
            cv.put(DBContract.MovieRatingsEntry.COLUMN_TITLE,title);
            cv.put(DBContract.MovieRatingsEntry.COLUMN_RATING,rating);

            dataBase.insert(DBContract.MovieRatingsEntry.TABLE_NAME,null,cv);
            dataBase.close();
            Toast.makeText(MainActivity.this,"rate saved",Toast.LENGTH_SHORT).show();
        }
    }
}
