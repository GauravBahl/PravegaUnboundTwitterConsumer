package com.unbound.pravega.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.io.Serializable;

@Data
public class TwitterResponseMapper implements Serializable {

    @SerializedName("text")
    private String tweet_text;
    @SerializedName("id_str")
    private String tweet_id;
    @SerializedName("created_at")
    private String tweet_date;
    @SerializedName("user")
    private TwitterUserMapper user;
    @SerializedName("retweet_count")
    private String retweets;
    @SerializedName("place")
    private TwitterPlaceMapper place;
    private String preds_label;



}

