package com.lcwd.rating.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Id;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//in Jpa with other data base we use @Entity for table structure we use
// and Jpa with mongodb we use @Document to store data in the form of collection
@Document("user_ratings")// in mongo Syntax is different @Entity is used to create table and @Document is used to create collection in mongodb data base
public class Rating {

    @Id//in mongo db Id is by default auto generated no need to create  custom
    private String ratingId;
    private String userId;
    private String hotelId;
    private int rating;
    private String feedback;

}
