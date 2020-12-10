package org.tms.finalproject.entity.order.converter;

import org.tms.finalproject.utils.OrderStatus;
import javax.persistence.AttributeConverter;

public class OrderStatusToStringConverter implements AttributeConverter<OrderStatus, String> {
    @Override
    public String convertToDatabaseColumn(OrderStatus orderStatus) {
        return orderStatus.getStatus();
    }

    @Override
    public OrderStatus convertToEntityAttribute(String s) {
        return OrderStatus.valueOf(s);
    }
}