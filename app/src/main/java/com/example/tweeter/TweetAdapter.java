package com.example.tweeter;

import android.app.Activity;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pichau on 29/08/2017.
 */

public class TweetAdapter extends BaseAdapter {

    private final List<Tweet> allTweets;
    private final Activity activity;

    public TweetAdapter(List<Tweet> allTweets, Activity activity) {
        this.allTweets = allTweets;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return allTweets.size();
    }

    @Override
    public Object getItem(int position) {
        return allTweets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater()
                .inflate(R.layout.tweet_layout, parent, false);
        Tweet tweet = allTweets.get(position);

        TextView author = (TextView) view.findViewById(R.id.tweetAuthor);
        TextView text = (TextView) view.findViewById(R.id.tweetText);
        TextView date = (TextView) view.findViewById(R.id.tweetDate);

        author.setText(tweet.getAuthor().getName());
        text.setText(tweet.getText());
        date.setText(tweet.getDate().toString());

        return view;
    }

    public List<Tweet> getList(){
        return new ArrayList<Tweet>(allTweets);
    }
}
