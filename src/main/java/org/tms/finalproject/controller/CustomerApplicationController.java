package org.tms.finalproject.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.tms.finalproject.dto.CommentDto;
import org.tms.finalproject.dto.MassageDto;
import org.tms.finalproject.dto.order.PaidOrderDto;
import org.tms.finalproject.dto.order.UnpaidOrderDto;
import org.tms.finalproject.entity.dialog.Dialog;
import org.tms.finalproject.entity.User;
import org.tms.finalproject.entity.order.Order;
import org.tms.finalproject.service.database.DialogService;
import org.tms.finalproject.service.database.OrderService;
import org.tms.finalproject.service.database.UserService;
import org.tms.finalproject.service.mapper.OrderDtoDoMapperService;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping(path = "/customer")
public class CustomerApplicationController {

    private OrderService orderService;
    private UserService userService;
    private DialogService dialogService;
    private OrderDtoDoMapperService orderDtoDoMapperService;

    public CustomerApplicationController(OrderService orderService,
                                         UserService userService,
                                         DialogService dialogService,
                                         OrderDtoDoMapperService orderDtoDoMapperService) {
        this.orderService = orderService;
        this.userService = userService;
        this.dialogService = dialogService;
        this.orderDtoDoMapperService = orderDtoDoMapperService;
    }

    @GetMapping(path = "/get-order-introduction")
    protected ModelAndView getOrderIntroduction(ModelAndView modelAndView) {
        modelAndView.setViewName("customer/order-introduction");
        return modelAndView;
    }

    @GetMapping(path = "/create-order")
    public ModelAndView getOrderForm(@RequestParam("order-type") String orderType,
                                     ModelAndView modelAndView) {
        if (orderType.equals("PAID_ORDER")) {
            modelAndView.addObject("orderDto", new PaidOrderDto());
            modelAndView.setViewName("customer/paid-order-form");
        } else if (orderType.equals("UNPAID_ORDER")) {
            modelAndView.addObject("orderDto", new UnpaidOrderDto());
            modelAndView.setViewName("customer/unpaid-order-form");
        } else {
            throw new IllegalArgumentException("Order type isn't correct!");
        }
        return modelAndView;
    }

    @PostMapping(path = "/create-paid-order")
    public ModelAndView createPaidOrder(@Valid @ModelAttribute("orderDto") PaidOrderDto orderDto,
                                    BindingResult bindingResult,
                                    ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("customer/paid-order-form");
        } else {
            Order order = orderDtoDoMapperService.buildDo(orderDto);
            orderService.createOrder(order);
            modelAndView.setViewName("redirect:/customer/get-all-created-orders");
        }
        return modelAndView;
    }

    @PostMapping(path = "/create-unpaid-order")
    public ModelAndView createUnpaidOrder(@Valid @ModelAttribute("orderDto") UnpaidOrderDto orderDto,
                                    BindingResult bindingResult,
                                    ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("customer/unpaid-order-form");
        } else {
            Order order = orderDtoDoMapperService.buildDo(orderDto);
            orderService.createOrder(order);
            modelAndView.setViewName("redirect:/customer/get-all-created-orders");
        }
        return modelAndView;
    }

    @GetMapping(path = "/get-all-created-orders")
    public ModelAndView showCustomerOrders(ModelAndView modelAndView,
                                           Authentication authentication) {
        String userName = userService.getActualUserName();
        List<Order> orders = orderService.getAllOrdersByAuthorLogin(userName);
        modelAndView.addObject("orders", orders);
        modelAndView.setViewName("customer/created-orders");
        return modelAndView;
    }

    @GetMapping(path = "/get-order-by-id")
    public ModelAndView getOrderById(@RequestParam("order-id") long orderId,
                                     ModelAndView modelAndView) {
        Order order = orderService.getOrderById(orderId);
        modelAndView.addObject("order", order);
        modelAndView.addObject("commentDto", new CommentDto());
        modelAndView.addObject("massageDto", new MassageDto());
        if (order.getStatus().getStatus().equals("IN_WORK_STATUS") && dialogService.existByDialogMembers(order.getAuthor(), order.getExecutor())) {
            Dialog dialog = dialogService.getDialogByDialogMembers(order.getAuthor(), order.getExecutor());
            modelAndView.addObject("dialog", dialog);
        }
        modelAndView.setViewName("customer/order");
        return modelAndView;
    }

    @GetMapping(path = "/approve-order")
    public ModelAndView approveOrder(@RequestParam("order-id") long orderId,
                                     @RequestParam("worker-id") long workerId,
                                     ModelAndView modelAndView) {
        User executor = userService.getUserById(workerId);
        orderService.approveOrder(orderId, executor);
        modelAndView.setViewName("redirect:/customer/get-all-created-orders");
        return modelAndView;
    }

    @GetMapping(path = "/close-order")
    public ModelAndView closeOrder(@RequestParam("order-id") long orderId,
                                   ModelAndView modelAndView) {
        orderService.closeOrderById(orderId);
        modelAndView.setViewName("redirect:/customer/get-all-created-orders");
        return modelAndView;
    }
}
