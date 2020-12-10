package org.tms.finalproject.service.mapper;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.tms.finalproject.dto.order.OrderDto;
import org.tms.finalproject.dto.order.PaidOrderDto;
import org.tms.finalproject.dto.order.UnpaidOrderDto;
import org.tms.finalproject.entity.Location;
import org.tms.finalproject.entity.order.Order;
import org.tms.finalproject.entity.order.PaidOrder;
import org.tms.finalproject.entity.order.UnpaidOrder;
import org.tms.finalproject.repository.UserRepository;

@Service
public class BaseOrderDtoDoMapperService implements OrderDtoDoMapperService {

    private UserRepository userRepository;

    public BaseOrderDtoDoMapperService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Order buildDo(OrderDto dto) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Order order = buildUniqueFields(dto);
        order.setAuthor(userRepository.findByUserName(principal.getUsername()).get());
        order.setTitle(dto.getTitle());
        order.setDescription(dto.getDescription());
        order.setLocation(buildLocationField(dto));
        return order;
    }



    private Order buildUniqueFields(OrderDto dto) {
        if (dto instanceof PaidOrderDto) {
            PaidOrder paidOrder = new PaidOrder();
            paidOrder.setPrice(((PaidOrderDto) dto).getPrice());
//            paidOrder.setOrderDeadlineDateTime(((PaidOrderDto) dto).getOrderDeadlineDateTime());
            return paidOrder;
        } else if (dto instanceof UnpaidOrderDto) {
            UnpaidOrder unpaidOrder = new UnpaidOrder();
            unpaidOrder.setPriority(((UnpaidOrderDto) dto).getPriority());
            return unpaidOrder;
        }
        throw new IllegalArgumentException("Dto type - isn't correct/null!");
    }

    private Location buildLocationField(OrderDto dto) {
        Location location = new Location();
        location.setCity(dto.getLocationCity());
        location.setStreet(dto.getLocationStreet());
        location.setHome(dto.getLocationHome());
        location.setFlat(dto.getLocationFlat());
        return location;
    }
}
