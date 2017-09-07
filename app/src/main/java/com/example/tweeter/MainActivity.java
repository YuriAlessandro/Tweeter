package com.example.tweeter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private List<Tweet> allTweets;
    TweetAdapter adapter;

    private static final int EDIT = 0;
    private static final int CAMERA = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allTweets = new ArrayList<Tweet>();
        final ListView tweetsList = (ListView) findViewById(R.id.tweets);
        this.adapter = new TweetAdapter(allTweets, this);
        tweetsList.setAdapter(adapter);

        tweetsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Context context = getApplicationContext();
                Intent intent = new Intent(context, TweetActivity.class);

                intent.putExtra(Tweet.TWEET_EXTRA, allTweets.get(position));
                startActivity(intent);
            }
        });

        tweetsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                Intent intent = new Intent(context, EditTweetActivity.class);

                intent.putExtra(Tweet.TWEET_EXTRA, allTweets.get(position));
                startActivityForResult(intent, EDIT);

                return true;
            }
        });
    }

    public void sendTweet(View view){
        EditText tweet = (EditText) findViewById(R.id.newTweet);
        String textNewTweet = tweet.getText().toString();

        Author author = new Author("@YuriAlessandro", "988893945");
        Tweet newTweet = new Tweet(new Date(), author, textNewTweet);

        allTweets.add(newTweet);
        adapter.notifyDataSetChanged();
    }

    public void setProfilePicture(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == EDIT && resultCode == RESULT_OK) {
                // get String data from Intent
                Tweet tweet = (Tweet) data.getSerializableExtra(Tweet.TWEET_EXTRA);
                int index = allTweets.indexOf(tweet);
                allTweets.set(index, tweet);
                adapter.notifyDataSetChanged();
            }else if(requestCode == CAMERA && resultCode == RESULT_OK){
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ImageView profileImage = (ImageView) findViewById(R.id.userPhoto);
                profileImage.setImageBitmap(photo);
            }
    }

}
