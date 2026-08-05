package com.shrucode.user_service.Utils;

import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "my-custom-stats")
public class MyCustomStatsEndpoint {

    //GET -> @ReadOperation -> /actuator/my-custom-stats invokes below method
    @ReadOperation
    public String readAll(){
        return "Hello ";
    }


    //GET -> @ReadOperation -> /actuator/my-custom-stats/Shruti/Hello there invokes below method selector name and selector message in sequence
    @ReadOperation
    public String read(@Selector String name , @Selector String message){
        return "Hello: "+ name + "msg for you is: "+message;
    }


    //POST -> @WriteOperation -> /actuator/my-custom-stats invokes below method
    @WriteOperation
    public String refresh(){
        return "refreshed";
    }

    //DELETE -> @DeleteOperation -> /actuator/my-custom-stats/key invokes below method matches selector key
    @DeleteOperation
    public String remove(@Selector String key){
        return "reset done for key: "+key;
    }

    //FOR POST AND DELETE OPERATOR requires authentication because they are critical and can change something
}
