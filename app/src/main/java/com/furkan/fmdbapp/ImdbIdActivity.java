package com.furkan.fmdbapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ImdbIdActivity extends AppCompatActivity {
    TextView tvYear,tvCast,tvRating,tvPlot,tvRuntime;
    ImageView moviePoster;
    EditText search_bar;
    Button button;
    RequestQueue mQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imdb_id);

        search_bar=findViewById(R.id.search_bar);
        tvYear=findViewById(R.id.tvYear);
        tvCast=findViewById(R.id.tvCast);
        tvRating=findViewById(R.id.tvRating);
        tvPlot=findViewById(R.id.tvPlot);
        tvRuntime=findViewById(R.id.tvRuntime);
        moviePoster=findViewById(R.id.moviePoster);
        button=(findViewById(R.id.button));
        mQueue= Volley.newRequestQueue(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });

    }
    private void jsonParse(){
        String imdbId=search_bar.getText().toString();
        String url="https://www.omdbapi.com/?i="+imdbId+"&plot=full&apikey=7693bcee";

        StringRequest request=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject movie=new JSONObject(response);
                            String result=movie.getString("Response");

                            if (result.equals("True")){
                                Toast.makeText(ImdbIdActivity.this,"found", Toast.LENGTH_SHORT).show();
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
                                String posterUrl=movie.getString("Poster");
                                if(posterUrl.equals("N/A")){
                                }
                                else{
                                    Picasso.get().load(posterUrl).into(moviePoster);
                                }
                            }
                            else {
                                Toast.makeText(ImdbIdActivity.this,"movie not found",Toast.LENGTH_SHORT).show();
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
}
