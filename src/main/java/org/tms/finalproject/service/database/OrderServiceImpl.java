package org.tms.finalproject.service.database;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tms.finalproject.dto.filter.FilterOrderDto;
import org.tms.finalproject.entity.User;
import org.tms.finalproject.entity.order.Order;
import org.tms.finalproject.repository.OrderRepository;
import org.tms.finalproject.utils.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private static final double MAX_VALUE = 1.8 * Math.pow(10, 308); //1.8е+308
    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order is null!");
        }
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(long orderId) {
        if (orderId < 1) {
            throw new IllegalArgumentException("Id is not correct!");
        }
        Optional<Order> order = orderRepository.findById(orderId);
        return order.orElseThrow(RuntimeException::new);
    }

    @Override
    public void takeOrder(long orderId, User applicant) {
        if (orderId < 1) {
            throw new IllegalArgumentException("OrderId is not correct!");
        } else if (applicant == null) {
            throw new IllegalArgumentException("Applicant is null!");
        }
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(RuntimeException::new);
        order.getApplicantsToOrder().add(applicant);
        order.setStatus(OrderStatus.AWAITING_APPROVAL_STATUS);
        orderRepository.save(order);
    }

    @Override
    public void approveOrder(long orderId, User executor) {
        if (orderId < 1) {
            throw new IllegalArgumentException("OrderId is not correct!");
        } else if (executor == null) {
            throw new IllegalArgumentException("Executor is null!");
        }
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(RuntimeException::new);
        order.setExecutor(executor);
        order.getApplicantsToOrder().clear();
        order.setStatus(OrderStatus.IN_WORK_STATUS);
        orderRepository.save(order);
    }

    @Override
    public void closeOrderById(long orderId) {
        if (orderId < 1) {
            throw new IllegalArgumentException("OrderId is not correct!");
        }
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(RuntimeException::new);
        order.setStatus(OrderStatus.CLOSED_STATUS);
        orderRepository.save(order);
    }

    @Override
    public void deleteOrderById(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Id is not correct!");
        }
        orderRepository.deleteOrderById(id);
    }

    @Override
    public List<Order> getAllOrders() { return orderRepository.findAllActiveOrders(); }

    @Override
    public List<Order> getAllOrdersByAuthorLogin(String authorLogin) {
        if (authorLogin == null) {
            throw new IllegalArgumentException("Author's login is null!");
        }
        return orderRepository.findAllByAuthor_UserName(authorLogin);
    }

    @Override
    public List<Order> getAllOrdersByFilterParameters(FilterOrderDto filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Filter is null!");
        }
        switch (filter.getOrderType()) {
            case "ALL_ORDER":
                return orderRepository.findAllOrdersByFilterParam(
                        filter.getLocationCity().equals("") ? "%" : filter.getLocationCity(),
                        filter.getLocationStreet().equals("") ? "%" : filter.getLocationStreet()
                );
            case "PAID_ORDER":
                return orderRepository.findPaidOrdersByFilterParam(
                        filter.getLocationCity().equals("") ? "%" : filter.getLocationCity(),
                        filter.getLocationStreet().equals("") ? "%" : filter.getLocationStreet(),
                        filter.getFromPrice(),
                        filter.getToPrice() == 0.0 ? MAX_VALUE : filter.getToPrice()
                );
            case "UNPAID_ORDER":
                return orderRepository.findUnpaidOrdersByFilterParam(
                        filter.getLocationCity().equals("") ? "%" : filter.getLocationCity(),
                        filter.getLocationStreet().equals("") ? "%" : filter.getLocationStreet()
                );
        }
        throw new IllegalArgumentException("Order type is not correct!");
    }

    @Override
    public List<Order> getAllOrdersByPartOfTitle(String line) {
        if (line == null) {
            throw new IllegalArgumentException("Line is null!");
        }
        return orderRepository.findAllOrdersByPartOfTitle("%" + line.trim() + "%");
    }

    @Override
    public List<Order> getAllTakenOrdersByUserName(String userName) {
        List<Order> allOrders = orderRepository.findAll();
        List<Order> resultOrders = allOrders.stream().filter(order -> {
            if (order.getApplicantsToOrder() != null) {
                for (User applicant : order.getApplicantsToOrder()) {
                    if (applicant.getUserName().equals(userName)) {
                        return true;
                    }
                }
            }
            return false;
        }).collect(Collectors.toList());
        resultOrders.addAll(orderRepository.findAllByExecutor_UserName(userName));
        return resultOrders;
    }

}
