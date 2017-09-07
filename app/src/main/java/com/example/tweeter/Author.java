package com.example.tweeter;

import java.io.Serializable;

/**
 * Created by Pichau on 29/08/2017.
 */

public class Author implements Serializable{
    private String name;
    private String tel;

    public Author(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
