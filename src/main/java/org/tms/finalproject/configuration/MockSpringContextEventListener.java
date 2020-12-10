package org.tms.finalproject.configuration;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.tms.finalproject.entity.Comment;
import org.tms.finalproject.entity.Location;
import org.tms.finalproject.entity.User;
import org.tms.finalproject.entity.order.Order;
import org.tms.finalproject.entity.order.PaidOrder;
import org.tms.finalproject.entity.order.UnpaidOrder;
import org.tms.finalproject.service.database.CommentService;
import org.tms.finalproject.service.database.OrderService;
import org.tms.finalproject.service.database.UserService;
import org.tms.finalproject.utils.OrderStatus;

@Component
public class MockSpringContextEventListener implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;
    private OrderService orderService;
    private CommentService commentService;

    public MockSpringContextEventListener(UserService userService, OrderService orderService, CommentService commentService) {
        this.userService = userService;
        this.orderService = orderService;
        this.commentService = commentService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        User customer = new User(0,"Oleg", "123", "CUSTOMER", 0.0, null, null);
        User worker = new User(0,"user", "user", "WORKER", 0.0, null, null);
        userService.createUser(customer);
        userService.createUser(worker);

        // First Order Mock:
        Location location1 = new Location(0, "Phoenix", "Cross", 25, 45);
        String title1 = "Subjugate a water pipe";
        String description1 = "The water pipe in my house is leaking. It is necessary to take measurements from the damaged area, buy the necessary parts, and replace the emergency network section.";
        Order order1 = new PaidOrder(0, title1, description1, customer, null, null, location1, OrderStatus.ACTIVE_STATUS, 55);

        // Second Order Mock:
        Location location2 = new Location(0, "San Antonio", "Rousvud", 12, 0);
        String title2 = "Harvest tomatoes from the greenhouse";
        String description2 = "I am an elderly farmer. There was an accident and I broke my leg, so I can't harvest on my own. Help is very much needed as the harvest time is running out and the fruits may deteriorate.";
        Order order2 = new UnpaidOrder(0, title2, description2, customer, null, null, location2, OrderStatus.ACTIVE_STATUS, "Wery");

        // Third Order Mock:
        Location location3 = new Location(0, "Dallas", "Cross", 34, 13);
        String title3 = "Driving a car";
        String description3 = "I am a very busy person. I agreed to buy a used car, but, unfortunately, I cannot come to the deal myself, I need a person who will do it for me.";
        Order order3 = new PaidOrder(0, title3, description3, customer, worker, null, location3, OrderStatus.IN_WORK_STATUS, 115);

        orderService.createOrder(order1);
        orderService.createOrder(order2);
        orderService.createOrder(order3);





        String comTitle1 = "Grate gay...";
        String comDescription1 = "Nice to work. Timely payment.";
        Comment commentForCustomer = new Comment(0, customer, 5.3, comTitle1, comDescription1, worker.getUserName());

        String comTitle2 = "Nice executor!";
        String comDescription2 = "The work was done efficiently and on time.";
        Comment commentForWorker = new Comment(0, worker, 6.5, comTitle2, comDescription2, customer.getUserName());

        String comTitle3 = "Nice !!!";
        String comDescription3 = "The work was done VERY efficiently.";
        Comment commentForWorker2 = new Comment(0, worker, 7.2, comTitle3, comDescription3, customer.getUserName());

        commentService.createComment(commentForCustomer);
        userService.recalculateAverageRatingForUserById(customer.getId());
        commentService.createComment(commentForWorker);
        commentService.createComment(commentForWorker2);
        userService.recalculateAverageRatingForUserById(worker.getId());
    }
}
