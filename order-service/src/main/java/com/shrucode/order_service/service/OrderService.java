package com.shrucode.order_service.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shrucode.order_service.common.*;
import com.shrucode.order_service.entity.Order;
import com.shrucode.order_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    @Autowired
    private InventoryFeignClient inventoryFeignClient;


    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String ENDPOINT_URL;
    private Logger log = LoggerFactory.getLogger(OrderService.class); // coming from Slf4j


    public TransactionResponse saveOrder(TransactionRequest transactionRequest) throws JsonProcessingException {

        String response = "";
        Order order = transactionRequest.getOrder();

        InventoryReduceRequest inventoryReduceRequest = new InventoryReduceRequest();
        inventoryReduceRequest.setProductId(order.getProductId());
        inventoryReduceRequest.setQuantity(order.getQuantity());
        InventoryReduceResponse inventoryReduceResponse = inventoryFeignClient.reduceInventory(inventoryReduceRequest);
        log.info("InventoryService Response: {}",new ObjectMapper().writeValueAsString(inventoryReduceResponse));//to view it in JSON MODE use ObjectMapper().writeValueAsString
        if (inventoryReduceResponse.getStatus()!=null &&InventoryStatus.Success.equals(inventoryReduceResponse.getStatus())){
            Payment payment = transactionRequest.getPayment();

            //set value in payment table which are being set from order table
            payment.setOrderId(order.getOrderId());
            payment.setAmount(order.getPrice());

            log.info("OrderService Request: {}",new ObjectMapper().writeValueAsString(transactionRequest));//to view it in JSON MODE use ObjectMapper().writeValueAsString
            orderRepository.save(order);
            //rest call(post call to payment api use RestTemplate connect 2 microservice make bean)
            //postForObject --> 3params --> url,request,response type class

            Payment payment1 = restTemplate.postForObject(ENDPOINT_URL, payment, Payment.class);
            log.info("PaymentService Response from order service REST CALL : {}",new ObjectMapper().writeValueAsString(payment1));//to view it in JSON MODE use ObjectMapper().writeValueAsString
            response = "success".equals(payment1 != null ? payment1.
                    getPaymentStatus() : null) ?
                       "Payment processed and order completed sucessfully" :
                       "Payment failed and order added to cart";
            return new TransactionResponse(order, payment1.getAmount(), response, payment1.getTransactionId());
        }else{
            response="Order failed due to insufficient stock";
            return new TransactionResponse(order,0, response, null);
        }
    }
}

   //to know which paymentid is associated with which order id??
   // at same time when saving order do a rest api call to payment api doPayment and pass the order id when payment object is saved to db we will map order id
   //add orderid and amount in payment entity and make 1 payment dao here also