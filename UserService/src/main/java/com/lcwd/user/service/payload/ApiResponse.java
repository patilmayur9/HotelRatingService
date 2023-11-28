package com.lcwd.user.service.payload;

import lombok.*;
import org.springframework.http.HttpStatus;
//all below @anotations are from lambok it provides getter setter consturcter etc
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder // to build the object after the all argument constructor is called then create the object of that class
public class ApiResponse {
    private String message;
    private Boolean success;
    private HttpStatus status;
}
