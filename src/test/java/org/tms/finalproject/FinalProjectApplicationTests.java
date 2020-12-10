package org.tms.finalproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.tms.finalproject.dto.order.PaidOrderDto;
import org.tms.finalproject.entity.Location;
import org.tms.finalproject.entity.User;
import org.tms.finalproject.entity.order.Order;
import org.tms.finalproject.entity.order.PaidOrder;
import org.tms.finalproject.repository.OrderRepository;
import org.tms.finalproject.repository.UserRepository;
import org.tms.finalproject.utils.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = FinalProjectApplication.class)
//@TestPropertySource(
//        locations = "classpath:application-integration-test.properties")
public class FinalProjectApplicationTests {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "worker", roles = "WORKER")
    public void whenGetOrders_thenReturnStatus() throws Exception {
        mvc.perform(get("/worker/get-all-orders")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "worker", roles = "WORKER")
    public void givenOrders_whenGetOrders_thenReturnStatusAndOrders() throws Exception {
        User author = new User();
        userRepository.save(author);
        User executor = new User();
        userRepository.save(executor);
        Location location = new Location();
        Order order = new PaidOrder(0, "title", "description", author, executor, new ArrayList<>(), location, OrderStatus.ACTIVE_STATUS, 12);
        orderRepository.save(order);
        List<Order> orders = Lists.newArrayList(order);

        mvc.perform(get("/worker/get-all-orders")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(model().attribute("orders", orders));
    }

    @Test
    @WithMockUser(username = "customer", roles = "CUSTOMER")
    public void givenOrderDto_whenCreateOrder_thenReturnStatusAndCreatedOrder() throws Exception {
        PaidOrderDto orderDto = new PaidOrderDto("Some a title of order", "description", "New-York", "Cross.st", 34, 0, 45);

        mvc.perform(post("/customer/create-paid-order")
                .content(objectMapper.writeValueAsString(orderDto))
//                .param()
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());

        Order order = orderRepository.findAllOrdersByPartOfTitle("title").get(0);
        assertThat(order.getTitle()).isEqualTo("Some a title of order");
    }
}
