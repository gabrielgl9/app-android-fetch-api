package com.example.requestapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.requestapi.model.Comment;
import com.example.requestapi.model.Photo;
import com.example.requestapi.model.Post;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity implements Response.Listener<JSONArray>,
                                                                  Response.ErrorListener {

    List <Comment> comments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent intent = getIntent();
        Post post = intent.getParcelableExtra("objPost");
        TextView title = (TextView) findViewById(R.id.titlePost);
        title.setText(post.getTitle());

        TextView body = (TextView) findViewById(R.id.bodyPost);
        body.setText(post.getBody());

        // RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://jsonplaceholder.typicode.com/comments?postId=" + post.getId();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                this, this);
        queue.add(jsonArrayRequest);
    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject json = response.getJSONObject(i);
                Comment obj = new Comment(json.getInt("id"),
                        json.getInt("postId"),
                        json.getString("name"),
                        json.getString("email"),
                        json.getString("body"));

                comments.add(obj);
            }

            LinearLayout linearLayout = findViewById(R.id.linearLayoutComments);
            for (Comment obj1 : comments) {
                TextView txtView = new TextView(this);
                txtView.setText(obj1.getName() + " - " + obj1.getEmail());
                TextView txtView2 = new TextView(this);
                txtView.setText(obj1.getBody());
                linearLayout.addView(txtView);
                linearLayout.addView(txtView2);
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