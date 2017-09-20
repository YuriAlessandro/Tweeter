package com.example.tweeter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private List<Tweet> allTweets;
    private TweetAdapter adapter;

    // UI Components
    private EditText tweet;

    private static final int EDIT = 0;
    private static final int CAMERA = 1;
    private static final int DIRECT = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize private UI Components
        this.tweet = (EditText) findViewById(R.id.newTweet);

        allTweets = new ArrayList<Tweet>();
        final ListView tweetsList = (ListView) findViewById(R.id.tweets);
        this.adapter = new TweetAdapter(allTweets, this);
        tweetsList.setAdapter(adapter);

        registerForContextMenu(tweetsList);

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
    }

    public void sendTweet(View view){
        String textNewTweet = this.tweet.getText().toString();

        Author author = new Author("@YuriAlessandro", "988893945");
        Tweet newTweet = new Tweet(new Date(), author, textNewTweet);

        this.allTweets.add(newTweet);
        this.adapter.notifyDataSetChanged();

        this.tweet.setText("");
    }

    public void sendDirect(View view){
        String textNewDirect = this.tweet.getText().toString();

        Author author = new Author("@YuriAlessandro", "988893945");
        Tweet newTweet = new Tweet(new Date(), author, textNewDirect);

        Context c = getApplicationContext();
        Intent i = new Intent(c, ChatActivity.class);

        startActivityForResult(i, DIRECT);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.refresh:
                Toast toast = Toast.makeText(MainActivity.this, "Refreshing...", Toast.LENGTH_SHORT);
                toast.show();
                return true;
            case R.id.logout:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tweets_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
        switch (item.getItemId()) {
            case R.id.edit:
                Context context = getApplicationContext();
                Intent intent = new Intent(context, EditTweetActivity.class);

                intent.putExtra(Tweet.TWEET_EXTRA, allTweets.get(position));
                startActivityForResult(intent, EDIT);
                return true;
            case R.id.delete:
                allTweets.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
