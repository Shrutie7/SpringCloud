package com.shrucode.gateway;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;



@RestController
public class FallbackController {

    @RequestMapping("/orderFallBack")
    public Mono<String> orderFallback(){
        return Mono.just("Order service took too long to respond Please try after some time!");
    }

    @RequestMapping("/paymentFallBack")
    public Mono<String> paymentFallback(){
        return Mono.just("Payment service took too long to respond Please try after some time!");
    }

    @RequestMapping("/inventoryFallBack")
    public Mono<String> inventoryFallBack(){
        return Mono.just("Inventory service took too long to respond Please try after some time!");
    }
}
