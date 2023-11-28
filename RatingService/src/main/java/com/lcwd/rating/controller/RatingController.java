package com.lcwd.rating.controller;

import com.lcwd.rating.entities.Rating;
import com.lcwd.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    RatingService ratingService;

    //create Rating
    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating) {
       return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));
    }


    //get all ratings
    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        return  ResponseEntity.ok(ratingService.getAllRating());
    }


    //get all by userId  wise ratings
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable String userId){
        return ResponseEntity.ok(ratingService.getRatingsByUserId(userId));
    }

    //get all by hotel wise ratings
     @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingByHotelID(@PathVariable String hotelId){
        return ResponseEntity.ok(ratingService.getRatingByHotelID(hotelId));
    }
}
