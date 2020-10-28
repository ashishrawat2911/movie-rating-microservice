package dev.ashish.moviecatalogservice.model;

import java.util.List;

public class UserRating {
    public UserRating() {
    }

    private List<Rating> ratings;
    private String userId;

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
