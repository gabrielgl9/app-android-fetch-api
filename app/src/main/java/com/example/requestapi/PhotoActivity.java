package com.example.requestapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.requestapi.model.Album;
import com.example.requestapi.model.Photo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity implements Response.Listener<JSONArray>,
                                                                Response.ErrorListener{
    List<Photo> photo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Intent intent = getIntent();
        Album album = intent.getParcelableExtra("objAlbum");
        TextView title = (TextView) findViewById(R.id.albumTextField);
        title.setText("Album: " + album.getTitle());

        // RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://jsonplaceholder.typicode.com/albums/" + album.getId() + "/photos";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                this, this);
        queue.add(jsonArrayRequest);
    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject json = response.getJSONObject(i);
                Photo obj = new Photo(json.getInt("id"),
                        json.getInt("albumId"),
                        json.getString("title"),
                        json.getString("url"),
                        json.getString("thumbnailUrl"));

                photo.add(obj);
            }

            LinearLayout linearLayout = findViewById(R.id.linearLayout);
            for (Photo obj1 : photo) {
                TextView txtView = new TextView(this);
                txtView.setText(obj1.getTitle());
                ImageView imgView = new ImageView(this);
                Picasso.with(PhotoActivity.this).load(obj1.getUrl() + ".png")
                                                .placeholder(R.drawable.ic_launcher_background)
                                                .into(imgView);
                linearLayout.addView(txtView);
                linearLayout.addView(imgView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String msg = error.getMessage();
        Toast.makeText(this.getApplicationContext(),"deu erro: "+msg, Toast.LENGTH_LONG).show();
    }
}