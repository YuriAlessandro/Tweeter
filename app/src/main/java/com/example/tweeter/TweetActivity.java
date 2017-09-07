package com.example.tweeter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TweetActivity extends AppCompatActivity {
    Intent intent;
    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_layout);

        this.intent = getIntent();
        this.tweet = (Tweet) intent.getSerializableExtra(Tweet.TWEET_EXTRA);

        TextView tweetText = (TextView) findViewById(R.id.tweetText);
        TextView tweetAuthor = (TextView) findViewById(R.id.tweetAuthor);
        TextView tweetDate = (TextView) findViewById(R.id.tweetDate);

        tweetText.setText(tweet.getText());
        tweetAuthor.setText(tweet.getAuthor().getName());
        tweetDate.setText(tweet.getDate().toString());

        tweetAuthor.setClickable(true);
    }

    public void callAuthor(View view){
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + tweet.getAuthor().getTel()));
        startActivity(callIntent);
    }
}
