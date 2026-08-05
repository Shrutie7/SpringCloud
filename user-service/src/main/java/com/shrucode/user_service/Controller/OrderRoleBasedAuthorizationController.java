package com.shrucode.user_service.Controller;


import com.shrucode.user_service.DTO.OrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderRoleBasedAuthorizationController {

    @GetMapping("/order-readRoleBase")
    @PreAuthorize("hasRole('USER') and hasAuthority('ORDER_READ')")
    @PostAuthorize("returnObject.userID == authentication.principal.id")
    //@PreAuthorize() annotation intercepted by AuthorizationManagerBeforeMethodInterceptor
    //string inside it is called spEl spring expression language, parse through SpelExpressionParser -> convert string into AST(Abstract syntax tree) , resolves the expression using OpAnd.java class and validate the result and decide whether to invoke the method or not  . can use and/or/!/not/#value
//
//    public ResponseEntity<String> readOrders(){
//        return ResponseEntity.ok("All orders has been fetched successfully");
//    }

    public OrderDTO readOrders(){
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.userID=1L;
        orderDTO.orderId = 10000L;

        return orderDTO;
    }


}
