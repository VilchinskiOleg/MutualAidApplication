package org.tms.finalproject;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.tms.finalproject.entity.Location;
import org.tms.finalproject.entity.User;
import org.tms.finalproject.entity.order.Order;
import org.tms.finalproject.entity.order.PaidOrder;
import org.tms.finalproject.repository.OrderRepository;
import org.tms.finalproject.service.database.OrderService;
import org.tms.finalproject.service.database.OrderServiceImpl;
import org.tms.finalproject.utils.OrderStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class OrderServiceImplIntegrationTest {

    @TestConfiguration
    static class OrderServiceImplTestContextConfiguration {
        @Bean
        public OrderService orderService(OrderRepository orderRepository) {
            return new OrderServiceImpl(orderRepository);
        }
    }

    @MockBean
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    @Before
    public void setUp() {
        Order o1 = new PaidOrder(
                0, "title1", "description1",
                new User(0, "Boris", null, null, 0.0, null, null),
                null, null,
                new Location(0, "New-York", "Manheton", 23, 87),
                OrderStatus.ACTIVE_STATUS, 35.0);
        List<Order> result = Lists.newArrayList(o1);

        Mockito.when(orderRepository.findAllByAuthor_UserName("Boris"))
                .thenReturn(result);
    }

    @Test
    public void whenValidName_thenOrderShouldBeFound() {
        String userName = "Boris";
        Order found = orderService.getAllOrdersByAuthorLogin(userName).get(0);

        assertThat(found.getAuthor().getUserName())
                .isEqualTo(userName);
    }
}
