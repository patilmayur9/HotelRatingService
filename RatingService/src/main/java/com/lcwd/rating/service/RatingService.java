package com.lcwd.rating.service;

import com.lcwd.rating.entities.Rating;

import java.util.List;

public interface RatingService {
    //create rating
    Rating create(Rating rating);

    //get all ratings
    List<Rating> getAllRating();

    //get all by userId  wise ratings
    List<Rating> getRatingsByUserId(String userId);

    //get all by hotel wise ratings
    List<Rating> getRatingByHotelID(String hotelID);

}
