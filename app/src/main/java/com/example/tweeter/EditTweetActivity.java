package com.example.tweeter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditTweetActivity extends AppCompatActivity {
    private EditText tweetText;
    private TextView tweetAuthor;
    private TextView tweetDate;
    private Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tweet);

        Intent i = getIntent();
        tweet = (Tweet) i.getSerializableExtra(Tweet.TWEET_EXTRA);

        tweetText = (EditText) findViewById(R.id.editTweetText);
        tweetAuthor = (TextView) findViewById(R.id.editTweetAuthor);
        tweetDate = (TextView) findViewById(R.id.editTweetDate);

        tweetText.setText(tweet.getText());
        tweetAuthor.setText(tweet.getAuthor().getName());
        tweetDate.setText(tweet.getDate().toString());

    }

    public void editTweet(View view){
        String newTweet = tweetText.getText().toString();
        tweet.setText(newTweet);

        Intent result = new Intent();
        result.putExtra(Tweet.TWEET_EXTRA, tweet);

        setResult(RESULT_OK, result);

        finish();
    }
}
