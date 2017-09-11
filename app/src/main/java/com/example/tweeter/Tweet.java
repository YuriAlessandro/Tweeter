package com.example.tweeter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Pichau on 29/08/2017.
 */

public class Tweet implements Serializable{

    public final static String TWEET_EXTRA = "tweet";
    private static int idCount = 0;
    private Date date;
    private Author author;
    private String text;
    private int id;


    public Tweet(Date date, Author author, String text) {
        this.date = date;
        this.author = author;
        this.text = text;
        id = ++idCount;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - hh:mm");
        return dateFormat.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Tweet) obj).id == id;
    }
}
