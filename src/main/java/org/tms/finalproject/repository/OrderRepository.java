package org.tms.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tms.finalproject.entity.order.Order;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "from Order o where o.status = 'ACTIVE_STATUS'")
    List<Order> findAllActiveOrders();

    @Query(value = "from Order o where o.status = 'ACTIVE_STATUS' and " +
            "o.location.city like :city and o.location.street like :street")
    List<Order> findAllOrdersByFilterParam(String city, String street);
    @Query(value = "from UnpaidOrder o where o.status = 'ACTIVE_STATUS' and " +
            "o.location.city like :city and o.location.street like :street")
    List<Order> findUnpaidOrdersByFilterParam(String city, String street);
    @Query(value = "from PaidOrder o where o.status = 'ACTIVE_STATUS' and o.location.city like :city and " +
            "o.location.street like :street and o.price >= :from and o.price <= :to")
    List<Order> findPaidOrdersByFilterParam(String city, String street, double from, double to);

    @Query(value = "from Order o where o.status = 'ACTIVE_STATUS' and o.title like :line")
    List<Order> findAllOrdersByPartOfTitle(String line);

    List<Order> findAllByAuthor_UserName(String userName);
    List<Order> findAllByExecutor_UserName(String userName);

    void deleteOrderById(long id);
}
