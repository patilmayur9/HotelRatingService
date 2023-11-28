package com.lcwd.user.service.services.impl;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.external.services.HotelService;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired// feign client hotel service injected declarative approach
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    /*
     RestTemplate is in built class so if we can use it then we need to explicitly tell
     the ioc container to create the bean of that class and inject where  i can used it
     but for that our custom  calss one of class is must uses the @Configuration so in that class we declare our bean
     and access here that bean  by default @SpringBootApplication annotation is having @Configuration so we can declare ther also
      other wise you need to create one config class annoted with @Configuration and in that declare n number of bean and use it
     */
    private RestTemplate restTemplate;
    @Override
    public User saveUser(User user) {
        //it generates unique userId for every time when user is created
        String randomUSerID = UUID.randomUUID().toString();
        user.setUserId(randomUSerID);
        return  userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        //get user from database with the help of user repository with user id
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! : "+userId));
        //fetch rating of the above user from   RATING-SERVICE  microservice
        // but RATING-SERVICE  microservice must have one rest controller of api to get the rating using usrId
        // to call the http api to other microservices  we need one http client for example (rest template, fin client ... etc) here we are using rest template to call RATING-SERVICE  microservice
        // http://localhost:8083/ratings/users/509231f9-88f0-4d97-8e0e-f36d11814493
                                                                    //localhost:8083 => RATING-SERVICE
       Rating [] ratingArray =  restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);// pass the url and just pass the second parameter what type of response you want here i want this particular user how many hotels rated give me that hotels list
        logger.info("--> {}",ratingArray);
        List<Rating> ratingOfUser = Arrays.stream(ratingArray).toList();
        List<Rating> ratingList =  ratingOfUser.stream().map(rating -> {
            // to api call to hetl service to get the hotel and then set to the rating then return the rating
            //http://localhost:8082/hotels/e6b0dee1-12e4-4dd8-baba-3a9a3b61dc6f
            //using rest template client                            //localhost:8082 => HOTEL-SERVICE
//           ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(),  Hotel.class);//restTemplate.getForEntity() this method is used to get more information about entity like status code.. for that we use getForEntity() other wise you use getForObject() also
//            Hotel hotel = forEntity.getBody();
//            logger.info( "response Status code: {}",forEntity.getStatusCode());

            Hotel hotel = hotelService.getHotel(rating.getHotelId());// using feign client we are calling HOTEL-SERVICE microservices
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingOfUser);
        return user;
    }
}


