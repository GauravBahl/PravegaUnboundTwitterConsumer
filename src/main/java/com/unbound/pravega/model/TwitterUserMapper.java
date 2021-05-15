package com.unbound.pravega.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class TwitterUserMapper implements Serializable {

    @SerializedName("screen_name")
    private String user_name;
    @SerializedName("location")
    private String user_location;
    @SerializedName("name")
    private String name;
    @SerializedName("followers_count")
    private String user_followers;

}
