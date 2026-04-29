package com.shrucode.order_service.common;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

////making seperate payment service for circuitbreaker
////Because your @Retry likely is not active due to same-class invocation.
//Instead, Rest template bubbles out directly.
@Service
public class PaymentCircuitBreakerService {
    @Autowired
    @Lazy
    private RestTemplate restTemplate;
    public static final String PAYMENT_SERVICE="paymentService";

    @Value("${microservice.payment-service.endpoints.endpoint.url}")
    private String ENDPOINT_URL;
    @CircuitBreaker(name= PAYMENT_SERVICE, fallbackMethod = "orderFallBackPayment")
    public Payment callPayment(Payment payment) {

        return restTemplate.postForObject(
                ENDPOINT_URL,
                payment,
                Payment.class
        );
    }

    public Payment orderFallBackPayment(Payment payment,Exception e){
        //keep same return type in fallBackmethod also as in saveOrder same exact signature

        Payment fallbackPayment = new Payment();

        fallbackPayment.setPaymentStatus("FAILED");
        fallbackPayment.setTransactionId(null);
        fallbackPayment.setAmount(0);

        return fallbackPayment;
    }
}
