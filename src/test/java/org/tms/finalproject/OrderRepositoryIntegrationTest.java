package org.tms.finalproject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.tms.finalproject.entity.Location;
import org.tms.finalproject.entity.User;
import org.tms.finalproject.entity.order.Order;
import org.tms.finalproject.entity.order.PaidOrder;
import org.tms.finalproject.repository.OrderRepository;
import org.tms.finalproject.utils.OrderStatus;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void whenFindByAuthorUserName_thenReturnOrder() {
        User author = new User(0, "Boris", null, null, 0.0, null, null);
        Order o1 = new PaidOrder(
                0, "title1", "description1",
                author,
                null, null,
                new Location(0, "New-York", "Manheton", 23, 87),
                OrderStatus.ACTIVE_STATUS, 35.0);
        entityManager.persist(author);
        entityManager.persist(o1);
        entityManager.flush();

        Order found = orderRepository.findAllByAuthor_UserName("Boris").get(0);

        assertThat(found.getAuthor().getUserName())
                .isEqualTo("kjhjhjk");
    }


}
