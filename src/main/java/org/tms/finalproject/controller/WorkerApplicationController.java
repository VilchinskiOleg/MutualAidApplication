package org.tms.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.tms.finalproject.dto.CommentDto;
import org.tms.finalproject.dto.MassageDto;
import org.tms.finalproject.dto.filter.FilterOrderDto;
import org.tms.finalproject.entity.dialog.Dialog;
import org.tms.finalproject.entity.User;
import org.tms.finalproject.entity.order.Order;
import org.tms.finalproject.service.database.DialogService;
import org.tms.finalproject.service.database.OrderService;
import org.tms.finalproject.service.database.UserService;
import java.util.List;

@Controller
@RequestMapping(path = "/worker")
public class WorkerApplicationController {

    private OrderService orderService;
    private UserService userService;
    private DialogService dialogService;

    public WorkerApplicationController(OrderService orderService,
                                       UserService userService,
                                       DialogService dialogService) {
        this.orderService = orderService;
        this.userService = userService;
        this.dialogService = dialogService;
    }

    @GetMapping(path = "/get-all-orders")
    public ModelAndView getAllOrders(ModelAndView modelAndView) {
        List<Order> orders = orderService.getAllOrders();
        modelAndView.addObject("orders", orders);
        modelAndView.addObject("filterDto", new FilterOrderDto());
        modelAndView.setViewName("worker/list-orders");
        return modelAndView;
    }

    @PostMapping(path = "/get-orders-by-alias")
    public ModelAndView getOrdersByAlias(@RequestParam("alias") String alias,
                                         ModelAndView modelAndView) {
        List<Order> orders = orderService.getAllOrdersByPartOfTitle(alias);
        modelAndView.addObject("orders", orders);
        modelAndView.addObject("filterDto", new FilterOrderDto());
        modelAndView.setViewName("worker/list-orders");
        return modelAndView;
    }

    @PostMapping(path = "/get-orders-by-filters")
    public ModelAndView getOrdersByFilters(@ModelAttribute("filterDto") FilterOrderDto filter,
                                           ModelAndView modelAndView) {
        List<Order> orders = orderService.getAllOrdersByFilterParameters(filter);
        modelAndView.addObject("orders", orders);
        modelAndView.setViewName("worker/list-orders");
        return modelAndView;
    }

    @GetMapping(path = "/get-taken-orders")
    public ModelAndView getTakenOrders(ModelAndView modelAndView) {
        String userName = userService.getActualUserName();
        List<Order> orders = orderService.getAllTakenOrdersByUserName(userName);
        modelAndView.addObject("orders", orders);
        modelAndView.setViewName("worker/progress");
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
        modelAndView.setViewName("worker/order");
        return modelAndView;
    }

    @GetMapping(path = "/take-order")
    public ModelAndView takeOrder(@RequestParam("order-id") long orderId,
                                  ModelAndView modelAndView) {
        User actualUser = userService.getActualUser();
        orderService.takeOrder(orderId, actualUser);
        modelAndView.setViewName("redirect:/worker/get-taken-orders");
        return modelAndView;
    }

    @GetMapping(path = "/delete-order")
    public ModelAndView deleteOrder(@RequestParam("order-id") long orderId,
                                    ModelAndView modelAndView) {
        orderService.deleteOrderById(orderId);
        modelAndView.setViewName("redirect:/worker/get-taken-orders");
        return modelAndView;
    }
}
