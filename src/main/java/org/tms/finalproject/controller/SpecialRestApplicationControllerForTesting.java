package org.tms.finalproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.tms.finalproject.dto.order.PaidOrderDto;
import org.tms.finalproject.entity.order.Order;
import org.tms.finalproject.service.database.OrderService;
import org.tms.finalproject.service.mapper.OrderDtoDoMapperService;

@RestController
@RequestMapping(path = "/test")
public class SpecialRestApplicationControllerForTesting {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDtoDoMapperService orderDtoDoMapperService;


    @PostMapping(path = "/create-paid-order")
    public void createPaidOrder(@RequestBody PaidOrderDto orderDto) {

//        Order order = orderDtoDoMapperService.buildDo(orderDto);
//        orderService.createOrder(order);
    }
}
