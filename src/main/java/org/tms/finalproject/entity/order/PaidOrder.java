package org.tms.finalproject.entity.order;

import lombok.*;
import org.tms.finalproject.entity.Location;
import org.tms.finalproject.entity.User;
import org.tms.finalproject.utils.OrderStatus;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(value = "PAID")

@Data
@NoArgsConstructor
public class PaidOrder extends Order {
    private double price;

    public PaidOrder(long id,
                     String title,
                     String description,
                     User author,
                     User executor,
                     List<User> applicantsToOrder,
                     Location location,
                     OrderStatus status,
                     double price) {
        super(id, title, description, author, executor, applicantsToOrder, location, status);
        this.price = price;
    }



    @Override
    public String toString() {
        return "Price: " + price;
    }
}
