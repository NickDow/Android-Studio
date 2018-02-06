package com.example.miyu1.loginapp.data.model;

/**
 * Created by Nick Dow on 11/1/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("level")
    @Expose
    private int level;

    @SerializedName("curr_exp")
    @Expose
    private int curr_exp;

    @SerializedName("needed_exp")
    @Expose
    private int needed_exp;

    public String getUsername(){
        return username;
    }

    public void setUsername(String inc_username){username = inc_username;}

    public String getPassword(){
        return password;
    }

    public void setPassword(String inc_password){
        password = inc_password;
    }

    public int getLevel(){ return level; }

    public void setLevel(int inc_level){ level = inc_level;}

    public int getCurr_exp(){ return curr_exp; }

    public void setCurr_exp(int inc_curr_exp){ curr_exp = inc_curr_exp; }

    public int getNeeded_exp(){ return needed_exp;}

    public void setNeeded_exp(int inc_needed_exp){ needed_exp = inc_needed_exp;}

    @Override
    public String toString(){
        return "Post{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", level='" + level + '\'' +
                ", current_exp='" + curr_exp + '\'' +
                ", needed_exp='" + needed_exp + '\'' +
                '}';
    }
}
