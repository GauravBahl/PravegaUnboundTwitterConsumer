package com.unbound.pravega.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.io.Serializable;

@Data
public class TwitterPlaceMapper implements Serializable {

    @SerializedName("name")
    private String tweet_cityname = "Missing";

    @SerializedName("country")
    private String tweet_countryname = "Missing";

    public TwitterPlaceMapper(){
        this.tweet_cityname = "Missing";
        this.tweet_countryname = "Missing";
    }
}
