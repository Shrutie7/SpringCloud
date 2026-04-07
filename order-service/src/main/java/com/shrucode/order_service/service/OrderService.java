package com.shrucode.order_service.service;


import com.shrucode.order_service.common.Payment;
import com.shrucode.order_service.common.TransactionRequest;
import com.shrucode.order_service.common.TransactionResponse;
import com.shrucode.order_service.entity.Order;
import com.shrucode.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;


    public TransactionResponse saveOrder(TransactionRequest transactionRequest){

        String response ="";
        Order order = transactionRequest.getOrder();
        Payment payment = transactionRequest.getPayment();

        //set value in payment table which are being set from order table
        payment.setOrderId(order.getOrderId());
        payment.setAmount(order.getPrice());


        //rest call(post call to payment api use RestTemplate connect 2 microservice make bean)
        //postForObject --> 3params --> url,request,response type class

        Payment payment1 = restTemplate.postForObject("http://localhost:9191/payment/savePayent", payment, Payment.class);

        response = payment1.getPaymentStatus().equals("success")? "Payment processed and order completed sucessfully":"Payment failed and order added to cart";

        orderRepository.save(order);
        return new TransactionResponse(order,payment1.getAmount(),response,payment1.getTransactionId());
    }
}



   //to know which paymentid is associated with which order id??
   // at same time when saving order do a rest api call to payment api doPayment and pass the order id when payment object is saved to db we will map order id
   //add orderid and amount in payment entity and make 1 payment dao here also